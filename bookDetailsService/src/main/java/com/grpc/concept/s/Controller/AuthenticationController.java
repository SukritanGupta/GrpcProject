package com.grpc.concept.s.Controller;

import com.grpc.concept.s.Model.AuthenticationResponse;
import com.grpc.concept.s.Model.Users;
import com.grpc.concept.s.RestDto.LoginDto;
import com.grpc.concept.s.UiService.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
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

//    logout functionality
//    Basically user able to logout in all the created token
   @PostMapping("/logout")
    public void logout(@RequestBody String token){

   }


}
