package com.nicolasf.qualibrate.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nicolasf.qualibrate.domain.Project;
import com.nicolasf.qualibrate.service.ProjectService;
import java.io.IOException;
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
public class ProjectControllerTest {

    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Mock
    private ProjectService projectService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        ProjectController imageController = new ProjectController(projectService);
        mockMvc = standaloneSetup(imageController).build();
    }

    @Test
    public void findAll() throws Exception {
        Project project = new Project();
        project.setCode("00C");
        project.setName("project1");
        List<Project> projects = Collections.singletonList(project);

        when(projectService.findAll()).thenReturn(projects);

        mockMvc.perform(get("/v1/api/projects")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].code", is("00C")))
                .andExpect(jsonPath("$[0].name", is("project1")));
    }

    @Test
    public void create() throws Exception {
        Project project = new Project();
        project.setCode("00C");
        project.setName("project1");

        when(projectService.create(anyObject())).thenReturn(project);

        mockMvc.perform(post("/v1/api/projects")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(project))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.code", is("00C")))
                .andExpect(jsonPath("$.name", is("project1")));

        verify(projectService, only()).create(anyObject());
        verifyNoMoreInteractions(projectService);
    }

    public byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

}