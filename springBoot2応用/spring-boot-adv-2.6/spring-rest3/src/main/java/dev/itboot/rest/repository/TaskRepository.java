package dev.itboot.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.itboot.rest.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
