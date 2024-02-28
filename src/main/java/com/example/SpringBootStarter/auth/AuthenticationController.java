package com.example.SpringBootStarter.auth;

import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringBootStarter.auth.dto.AuthenticationRequest;
import com.example.SpringBootStarter.auth.dto.AuthenticationResponse;
import com.example.SpringBootStarter.auth.dto.RefreshTokenRequest;
import com.example.SpringBootStarter.auth.dto.RegisterRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
      @RequestBody RegisterRequest request
  ) throws BadRequestException {
    return ResponseEntity.ok(service.registerUser(request));
  }
  
  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest request
  ) {
    return ResponseEntity.ok(service.authenticate(request));
  }

  @PostMapping("/refresh")
  public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest request) throws BadRequestException {
    System.out.println("Tokennnnnn: " + request.getRefreshToken());

    return ResponseEntity.ok(service.refreshToken(request));
  }
}
