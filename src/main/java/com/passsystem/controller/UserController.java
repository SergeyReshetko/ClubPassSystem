package com.passsystem.controller;

import com.passsystem.model.User;
import com.passsystem.model.UserSearchFilter;
import com.passsystem.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    private final UserService userService;
    
    @GetMapping("/{id:\\d+}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        logger.info("getUserById: id={}", id);
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id));
    }
    
    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers(
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "pageNumber", required = false) Integer pageNumber
    ) {
        logger.info("Called getAllUsers");
        var filter = new UserSearchFilter(
                pageSize,
                pageNumber
        );
        return ResponseEntity.ok(userService.searchAllByFilter(filter));
    }
    
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        logger.info("createUser: user={}", user);
        return ResponseEntity.status(HttpStatus.CREATED)
                       .header("test-header", "123")
                       .body(userService.addUser(user));
    }
}