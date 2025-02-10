package com.FrumoStore.service;

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

}
