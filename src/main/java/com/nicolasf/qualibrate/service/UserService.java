package com.nicolasf.qualibrate.service;

import com.nicolasf.qualibrate.domain.User;
import com.nicolasf.qualibrate.exception.EntityNotFoundException;
import java.util.List;

public interface UserService {

    List<User> findAll();

    User find(Long id) throws EntityNotFoundException;

    void delete(Long id);

    User update(Long id, String firstName, String lastName, String email) throws EntityNotFoundException;

    void assignProject(Long userId, Long projectId) throws EntityNotFoundException;

}
