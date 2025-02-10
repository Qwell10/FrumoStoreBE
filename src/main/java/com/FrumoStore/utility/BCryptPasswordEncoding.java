package com.FrumoStore.utility;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class BCryptPasswordEncoding {

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public String encodePassword(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    public boolean authenticate(String rawPassword, String passwordHash) {
        return encoder.matches(rawPassword, passwordHash);
    }
}