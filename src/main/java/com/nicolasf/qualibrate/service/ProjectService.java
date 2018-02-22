package com.nicolasf.qualibrate.service;

import com.nicolasf.qualibrate.domain.Project;
import com.nicolasf.qualibrate.exception.EntityNotFoundException;
import java.util.List;

public interface ProjectService {

    Project find(Long id) throws EntityNotFoundException;

    List<Project> findAll();

    Project create(Project project);

}
