package com.grpc.concept.s.UiService;


import com.grpc.concept.s.Model.AuthenticationResponse;
import com.grpc.concept.s.Model.Token;
import com.grpc.concept.s.Model.Users;
import com.grpc.concept.s.Repo.TokenRepository;
import com.grpc.concept.s.Repo.User_Repo;
import com.grpc.concept.s.RestDto.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {
    @Autowired
    private User_Repo user_repo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthenticationResponse register(Users request) {

        // check if user already exist. if exist than authenticate the user
        if(user_repo.findByUsername(request.getUsername()).isPresent()) {
            return new AuthenticationResponse(null, "User already exist");
        }

        Users user = new Users();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));


        user.setRole(request.getRole());

        user = user_repo.save(user);

        String jwt = jwtService.generateToken(user);

        saveUserToken(jwt, user);

        return new AuthenticationResponse(jwt, "User registration was successful");

    }

    public AuthenticationResponse authenticate(LoginDto request) {
//        Here First I have Authenticate the user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserName(),
                        request.getPassword()
                )
        );

        Users user = user_repo.findByUsername(request.getUserName()).orElseThrow();
        String jwt = jwtService.generateToken(user);

        revokeAllTokenByUser(user);
        saveUserToken(jwt, user);

        return new AuthenticationResponse(jwt, "User login was successful");

    }
//    Here I logged out the user token, and save that value in database
    private void revokeAllTokenByUser(Users user) {
        List<Token> validTokens = tokenRepository.findAllTokensByUser(user.getId());
        if(validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t-> {
            t.setLoggedOut(true);
        });

        tokenRepository.saveAll(validTokens);
    }
//    Here I save the new token
    private void saveUserToken(String jwt, Users user) {
        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }


}
