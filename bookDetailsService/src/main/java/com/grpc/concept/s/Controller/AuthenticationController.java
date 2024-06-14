package com.grpc.concept.s.Controller;

import com.grpc.concept.s.Model.AuthenticationResponse;
import com.grpc.concept.s.Model.Users;
import com.grpc.concept.s.RestDto.LoginDto;
import com.grpc.concept.s.UiService.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@CrossOrigin(origins = "http://localhost:4500")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody Users request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody LoginDto request
    ) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
//    Here I write one get method to get the token from token repository according to userName and password


}
