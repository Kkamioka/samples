package dev.itboot.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dev.itboot.rest.model.Task;

@RepositoryRestResource
public interface TaskRepository extends JpaRepository<Task, Long> {
}
