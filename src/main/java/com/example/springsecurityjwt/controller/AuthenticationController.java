package com.example.springsecurityjwt.controller;

import com.example.springsecurityjwt.model.jwt.AuthenticationRequest;
import com.example.springsecurityjwt.model.jwt.AuthenticationResponse;
import com.example.springsecurityjwt.service.UsersService;
import com.example.springsecurityjwt.util.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UsersService usersService;

    @PostMapping("/create-token")
    public ResponseEntity<AuthenticationResponse> create(@RequestBody AuthenticationRequest authenticationRequest) {
        AuthenticationResponse authenticationResponse = new AuthenticationResponse(null,null,false);
        try{
            UserDetails userDetails = usersService.loadUserByUsername(authenticationRequest.getUsername());
            if(userDetails.getPassword().equals(authenticationRequest.getPassword())){
                authenticationResponse.setToken(jwtTokenProvider.generateToken(userDetails));
                authenticationResponse.setResult(true);
                authenticationResponse.setRoles(jwtTokenProvider.getRoles(authenticationResponse.getToken()));
                return ResponseEntity.ok(authenticationResponse);
            }
            return ResponseEntity.status(400).body(authenticationResponse);
        }catch (Exception e){
            return ResponseEntity.status(400).body(authenticationResponse);
        }
    }

    @PostMapping("/control")
    public ResponseEntity<AuthenticationResponse> check(@RequestBody AuthenticationResponse authenticationResponse) {
        UserDetails userDetails;
        authenticationResponse.setResult(false);
        try{
            String username = jwtTokenProvider.extractUsername(authenticationResponse.getToken());
            try {
                userDetails = usersService.loadUserByUsername(username);
            }catch (UsernameNotFoundException e){
                return ResponseEntity.status(400).body(authenticationResponse);
            }
            if(jwtTokenProvider.validateToken(authenticationResponse.getToken(),userDetails)){
                authenticationResponse.setResult(true);
                authenticationResponse.setRoles(jwtTokenProvider.getRoles(authenticationResponse.getToken()));
                return ResponseEntity.ok(authenticationResponse);
            }
            return ResponseEntity.status(400).body(authenticationResponse);
        }catch (Exception e){
            return ResponseEntity.status(400).body(authenticationResponse);
        }
    }

}
