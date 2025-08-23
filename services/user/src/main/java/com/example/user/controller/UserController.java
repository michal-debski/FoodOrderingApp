package com.example.user.controller;

import com.example.user.business.User;
import com.example.user.business.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    private final UserMapper userMapper;


    @GetMapping("/{id}")
    public ResponseEntity<?> findUser(@PathVariable int id
    ) {
        User userById = userService.findUserById(id);
        UserDTO userDTO = userMapper.mapToDTO(userById);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        User user = userMapper.map(userDTO);
        UserDTO savedUserDTO = userMapper.mapToDTO(
                userService.saveUser(user)
        );
        return new ResponseEntity<>(savedUserDTO, HttpStatus.CREATED);

    }
}
