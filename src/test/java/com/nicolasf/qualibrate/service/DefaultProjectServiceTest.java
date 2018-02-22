package com.nicolasf.qualibrate.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nicolasf.qualibrate.domain.Project;
import com.nicolasf.qualibrate.repository.ProjectRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class DefaultProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    private DefaultProjectService projectService;

    @Before
    public void setUp() {
        projectService = new DefaultProjectService(projectRepository);
    }

    @Test
    public void findUser() throws Exception {
        Project project = new Project();
        project.setName("project1");
        project.setCode("00C");
        when(projectRepository.findOne(1L)).thenReturn(project);

        Project projectFound = projectService.find(1L);

        Assertions.assertThat(projectFound)
                .isNotNull()
                .extracting("name", "code")
                .contains("project1", "00C");
    }

    @Test
    public void findAllUsers() throws Exception {
        projectService.findAll();

        verify(projectRepository).findAll();
    }

    @Test
    public void createUser() throws Exception {
        Project project = new Project();
        project.setName("project1");
        project.setCode("00C");

        projectService.create(project);

        verify(projectRepository).save(project);
    }

}