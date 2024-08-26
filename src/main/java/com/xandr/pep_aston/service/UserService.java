package com.xandr.pep_aston.service;

import com.xandr.pep_aston.entity.User;
import com.xandr.pep_aston.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    public final UserRepository userRepository;

    public Optional<User> findByNameAndPin(String name, String pin) {

        return userRepository.findAllByNameAndPin(name, pin);
    }
}
