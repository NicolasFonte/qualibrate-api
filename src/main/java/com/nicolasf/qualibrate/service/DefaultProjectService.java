package com.nicolasf.qualibrate.service;


import com.nicolasf.qualibrate.domain.Project;
import com.nicolasf.qualibrate.exception.EntityNotFoundException;
import com.nicolasf.qualibrate.repository.ProjectRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DefaultProjectService implements ProjectService {

    private final ProjectRepository projectRepository;

    public DefaultProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Project find(Long id) throws EntityNotFoundException {
        Project project = projectRepository.findOne(id);
        if (project == null) {
            throw new EntityNotFoundException(String.format("project id %d not found", id));
        }

        return project;
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public Project create(Project project) {
        return projectRepository.save(project);
    }
}
