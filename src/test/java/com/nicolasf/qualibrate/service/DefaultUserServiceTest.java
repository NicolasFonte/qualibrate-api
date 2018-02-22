package com.nicolasf.qualibrate.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nicolasf.qualibrate.domain.Project;
import com.nicolasf.qualibrate.domain.User;
import com.nicolasf.qualibrate.exception.EntityNotFoundException;
import com.nicolasf.qualibrate.repository.UserRepository;
import org.assertj.core.groups.Tuple;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class DefaultUserServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProjectService projectService;

    private DefaultUserService userService;

    @Before
    public void setUp() throws Exception {
        userService = new DefaultUserService(userRepository, projectService);
    }

    @Test
    public void findAllUsers() throws Exception {
        userService.findAll();

        verify(userRepository).findAll();
    }

    @Test
    public void findUser() throws Exception {
        User user = new User();
        user.setFirstName("john");
        user.setLastName("fux");
        user.setEmail("email@email.com");

        when(userRepository.findOne(1L)).thenReturn(user);

        User userFound = userService.find(1L);

        assertThat(userFound)
                .extracting("firstName", "lastName", "email")
                .contains("john", "fux", "email@email.com");
    }

    @Test
    public void findUserNotFoundThrownEntityNotFound() throws Exception {
        when(userRepository.findOne(1L)).thenReturn(null);

        thrown.expect(EntityNotFoundException.class);
        thrown.expectMessage("user id 1 not found");

        userService.find(1L);
    }

    @Test
    public void deleteUser() throws Exception {
        userService.delete(1L);

        verify(userRepository, only()).delete(1L);
    }

    @Test
    public void updateUser() throws Exception {
        User user = new User();
        user.setFirstName("john");
        user.setLastName("fux");
        user.setEmail("email@email.com");

        when(userRepository.findOne(1L)).thenReturn(user);

        userService.update(1L, "updateFirstName", "updateLastName", "updateEmail");

        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(argument.capture());
        assertThat(argument.getValue())
                .extracting("firstName", "lastName", "email")
                .contains("updateFirstName", "updateLastName", "updateEmail");
    }

    @Test
    public void assignProject() throws Exception {
        Project project = new Project();
        project.setName("projectName");
        project.setCode("00C");
        when(projectService.find(1L)).thenReturn(project);

        User user = new User();
        user.setFirstName("john");
        user.setLastName("fux");
        user.setEmail("email@email.com");
        when(userRepository.findOne(1L)).thenReturn(user);

        userService.assignProject(1L, 1L);

        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(argument.capture());
        assertThat(argument.getValue().getProjects())
                .hasSize(1)
                .extracting("name", "code")
                .contains(Tuple.tuple("projectName", "00C"));

    }

}