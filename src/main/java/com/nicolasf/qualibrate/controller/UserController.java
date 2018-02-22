package com.nicolasf.qualibrate.controller;

import com.nicolasf.qualibrate.domain.User;
import com.nicolasf.qualibrate.exception.EntityNotFoundException;
import com.nicolasf.qualibrate.service.UserService;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User findUser(@PathVariable Long id) throws EntityNotFoundException {
        return userService.find(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) throws EntityNotFoundException {
        userService.delete(id);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email) throws EntityNotFoundException {
        return userService.update(id, firstName, lastName, email);
    }

    @PutMapping("/{userId}/projects/{projectId}")
    public void assignProject(@PathVariable Long userId, @PathVariable Long projectId) throws EntityNotFoundException {
        userService.assignProject(userId, projectId);
    }


}
