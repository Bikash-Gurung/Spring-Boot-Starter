package com.example.SpringBootStarter.auth;

import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.SpringBootStarter.auth.dto.AuthenticationRequest;
import com.example.SpringBootStarter.auth.dto.AuthenticationResponse;
import com.example.SpringBootStarter.auth.dto.RefreshTokenRequest;
import com.example.SpringBootStarter.auth.dto.RegisterRequest;
import com.example.SpringBootStarter.config.JwtService;
import com.example.SpringBootStarter.token.Token;
import com.example.SpringBootStarter.token.TokenRepository;
import com.example.SpringBootStarter.user.Role;
import com.example.SpringBootStarter.user.User;
import com.example.SpringBootStarter.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository userRepository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse registerUser(RegisterRequest request) throws BadRequestException {

    var existingUser = userRepository.findByEmail(request.getEmail().trim());

    if(existingUser.isPresent()){
      throw new BadRequestException("Email already taken.");
    }

    var user = User.builder()
        .firstname(request.getFirstname())
        .lastname(request.getLastname())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(Role.USER)
        .build();
    var savedUser = userRepository.save(user);
    var accessToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    saveUserToken(savedUser, accessToken, refreshToken);
    
    return AuthenticationResponse.builder()
        .accessToken(accessToken)
            .refreshToken(refreshToken)
        .build();
  }

  public void registerAdmin(RegisterRequest request) {
    var existingUser = userRepository.findByEmail(request.getEmail().trim());

    if(existingUser.isPresent()){
      return;
    }

    var user = User.builder()
        .firstname(request.getFirstname())
        .lastname(request.getLastname())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(Role.ADMIN)
        .build();
    var savedUser = userRepository.save(user);
    var accessToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    saveUserToken(savedUser, accessToken, refreshToken);
    revokeAllUserTokens(savedUser);
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
    var user = userRepository.findByEmail(request.getEmail())
        .orElseThrow();
    revokeAllUserTokens(user);
    var accessToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    saveUserToken(user, accessToken, refreshToken);

    return AuthenticationResponse.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
  }

  private void saveUserToken(User user,String accessToken, String refreshToken) {
    var token = Token.builder()
        .user(user)
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }

  public void revokeAllUserTokens(User user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }

  public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) throws BadRequestException {
    final String refreshToken = refreshTokenRequest.getRefreshToken();
    final String userEmail = jwtService.extractUsername(refreshToken);

    if (userEmail == null) {
      System.out.println("No email");
      throw new BadRequestException("Invalid refresh token");
    }

    var user = userRepository.findByEmail(userEmail)
            .orElseThrow();
    if (!jwtService.isTokenValid(refreshToken, user)) {
      System.out.println("invalid refresh token");
      throw new BadRequestException("Invalid refresh token");
    }

    var existingRefreshToken = tokenRepository.findByRefreshToken(refreshToken);
    if(!existingRefreshToken.isPresent()){
      System.out.println("Refresh token does not exists");
      throw new BadRequestException("Invalid refresh token");
    }

    revokeAllUserTokens(user);
    var newAccessToken = jwtService.generateToken(user);
    var newRefreshToken = jwtService.generateRefreshToken(user);
    saveUserToken(user, newAccessToken, newRefreshToken);
    var authResponse = AuthenticationResponse.builder()
            .accessToken(newAccessToken)
            .refreshToken(newRefreshToken)
            .build();

    System.out.println(authResponse);
        
    return authResponse;
  }
}
