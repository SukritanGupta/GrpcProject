package com.grpc.concept.s.UiService;


import com.grpc.concept.s.Repo.User_Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
    @Autowired
    private User_Repo repo;
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repo.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }
}
