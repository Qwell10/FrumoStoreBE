package com.FrumoStore.controller;

import com.FrumoStore.dto.LoginResponse;
import com.FrumoStore.dto.UserDto;
import com.FrumoStore.entity.UserEntity;
import com.FrumoStore.exception.ErrorResponse;
import com.FrumoStore.exception.InvalidPasswordException;
import com.FrumoStore.exception.UserNotFoundException;
import com.FrumoStore.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public LoginResponse loginUser(@RequestBody UserDto userDto, HttpServletRequest request) {
        try {
            UserEntity user = userService.loginUser(userDto.getNickname(), userDto.getPassword());

            HttpSession session = request.getSession();
            session.setAttribute("userId", user.getId());
            session.setMaxInactiveInterval(1800); //1800 sec = 30 min

            return new LoginResponse(true, "You were logged in.");

        } catch (UserNotFoundException e) {
            return new LoginResponse(false, "Invalid username or password.");
        } catch (Exception e) {
            e.printStackTrace();
            return new LoginResponse(false, "An error occurred during login.");
        }
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
        try {
            userService.registerUser(userDto);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFoundException(UserNotFoundException e) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler(value = InvalidPasswordException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleInvalidPasswordException(InvalidPasswordException e) {
        return new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
    }

}
