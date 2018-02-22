package com.nicolasf.qualibrate.service;

import com.nicolasf.qualibrate.domain.Project;
import com.nicolasf.qualibrate.domain.User;
import com.nicolasf.qualibrate.exception.EntityNotFoundException;
import com.nicolasf.qualibrate.repository.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final ProjectService projectService;

    public DefaultUserService(UserRepository userRepository, ProjectService projectService) {
        this.userRepository = userRepository;
        this.projectService = projectService;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User find(Long id) throws EntityNotFoundException {
        return findUser(id);
    }

    @Override
    public void delete(Long id) {
        userRepository.delete(id);
    }

    @Override
    public User update(Long id, String firstName, String lastName, String email) throws EntityNotFoundException {
        User user = findUser(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);

        return userRepository.save(user);
    }

    @Override
    public void assignProject(Long userId, Long projectId) throws EntityNotFoundException {
        Project project = projectService.find(projectId);
        User user = findUser(userId);
        user.getProjects().add(project);

        userRepository.save(user);
    }

    private User findUser(Long userId) throws EntityNotFoundException {
        User user = userRepository.findOne(userId);
        if (user == null) {
            throw new EntityNotFoundException(String.format("user id %d not found", userId));
        }

        return user;
    }
}
