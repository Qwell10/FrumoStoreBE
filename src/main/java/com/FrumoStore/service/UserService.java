package com.FrumoStore.service;

import com.FrumoStore.dto.UserDto;
import com.FrumoStore.entity.UserEntity;
import com.FrumoStore.exception.InvalidPasswordException;
import com.FrumoStore.exception.UserNotFoundException;
import com.FrumoStore.repository.UserRepository;
import com.FrumoStore.utility.BCryptPasswordEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoding encoder;

    public void registerUser(UserDto userDto) {
        if (userDto.getNickname() == null || userDto.getNickname().isEmpty()) {
            throw new IllegalArgumentException("Nickname cannot be empty");
        } else if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        String encodedPassword = encoder.encodePassword(userDto.getPassword());

        UserEntity userEntity = new UserEntity(userDto.getNickname(), encodedPassword);

        userRepository.save(userEntity);
    }

    public UserEntity loginUser(String nickname, String enteredPassword) {
        Optional<UserEntity> userOptional = userRepository.findByNickname(nickname);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User %s not found", nickname);
        }

        UserEntity user = userOptional.get();

        if (encoder.authenticate(enteredPassword, user.getPassword())) {
            return user;

        } else throw new InvalidPasswordException("Invalid password");
    }

/*
    public UserEntity loginUser(String nickname, String enteredPassword) {
        Optional<UserEntity> userOptional = userRepository.findByNickname(nickname);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User %s not found", nickname);
        }

        UserEntity user = userOptional.get();

        if (user.getPassword().equals(enteredPassword)) {
            return user;

        } else throw new InvalidPasswordException("Invalid password");
    }
*/

}
