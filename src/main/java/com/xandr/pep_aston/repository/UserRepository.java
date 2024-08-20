package com.xandr.pep_aston.repository;

import com.xandr.pep_aston.entity.User;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends Repository<User, Integer> {
    Optional<User> findById(Integer id);

    Optional<User> findAllByNameAndPin(String name, Integer pin);

    List<User> findAll();
}