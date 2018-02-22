package com.nicolasf.qualibrate.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.nicolasf.qualibrate.domain.Project;
import com.nicolasf.qualibrate.domain.User;
import com.nicolasf.qualibrate.service.UserService;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        UserController imageController = new UserController(userService);
        mockMvc = standaloneSetup(imageController).build();
    }

    @Test
    public void findAllAPI() throws Exception {
        User user = new User();
        user.setEmail("email@email.com");
        user.setFirstName("first");
        user.setLastName("last");
        List<User> users = Collections.singletonList(user);

        when(userService.findAll()).thenReturn(users);

        mockMvc.perform(get("/v1/api/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].email", is("email@email.com")))
                .andExpect(jsonPath("$[0].firstName", is("first")))
                .andExpect(jsonPath("$[0].lastName", is("last")));
    }

    @Test
    public void findUserAPI() throws Exception {
        User user = new User();
        user.setEmail("email@email.com");
        user.setFirstName("first");
        user.setLastName("last");

        when(userService.find(1L)).thenReturn(user);

        mockMvc.perform(get("/v1/api/users/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.email", is("email@email.com")))
                .andExpect(jsonPath("$.firstName", is("first")))
                .andExpect(jsonPath("$.lastName", is("last")));

    }

    @Test
    public void deleteUser() throws Exception {
        User user = new User();
        user.setEmail("email@email.com");
        user.setFirstName("first");
        user.setLastName("last");

        doNothing().when(userService).delete(1L);

        mockMvc.perform(delete("/v1/api/users/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().is2xxSuccessful());

        verify(userService, only()).delete(1L);
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void updateUser() throws Exception {
    }

    @Test
    public void assignProject() throws Exception {
    }

}