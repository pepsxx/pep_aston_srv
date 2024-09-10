package com.xandr.pep_aston.service;

import com.xandr.pep_aston.dto.UserDto;
import com.xandr.pep_aston.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;
/** Сервис по работе с пользователями
 */
@Service
public interface UserService {
    /**
     * @param name - имя пользователя
     * @param pin - пинкод пользователя
     * @return Optional<User>
     */
    Optional<User> findByNameAndPin(String name, String pin);

    Optional<UserDto> create(UserDto userDto);
}
