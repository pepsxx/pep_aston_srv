package com.xandr.pep_aston.repository;

import com.xandr.pep_aston.entity.User;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UserRepository extends Repository<User, Long> {

    Optional<User> findAllByNameAndPin(String name, String pin);
}