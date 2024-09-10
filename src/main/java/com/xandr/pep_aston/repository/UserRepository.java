package com.xandr.pep_aston.repository;

import com.xandr.pep_aston.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findAllByNameAndPin(String name, String pin);

    Optional<User> findFirstByName(String name);

}