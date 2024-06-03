package com.grpc.concept.s.Repo;

import com.grpc.concept.s.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//Here I create the user Repository
@Repository
public interface User_Repo extends JpaRepository<Users,Integer> {
    Optional<Users> findByUsername(String username);
}
