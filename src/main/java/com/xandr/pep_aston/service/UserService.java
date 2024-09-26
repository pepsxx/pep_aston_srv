package com.xandr.pep_aston.service;

import com.xandr.pep_aston.dto.UserDto;
import com.xandr.pep_aston.entity.User;

import java.util.Optional;

/** Сервис по работе с пользователями
 */
public interface UserService {

    /**
     * Проверяет соответствие пин-кода пользователю
     * @param name Имя пользователя
     * @param pin Пин-код пользователя
     * @return Optional.of(User) если пин-код принадлежит пользователю, в противном случае Optional.empty()
     */
    Optional<User> findByNameAndPin(String name, String pin);

    /**
     * Создает нового пользователя
     * @param userDto (name, pin)
     * @return Optional.of(UserDto), но если пользователь уже существует, то Optional.empty().
     */
    Optional<UserDto> create(UserDto userDto);
}
