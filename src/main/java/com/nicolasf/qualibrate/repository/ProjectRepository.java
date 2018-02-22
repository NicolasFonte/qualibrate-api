package com.nicolasf.qualibrate.repository;

import com.nicolasf.qualibrate.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
