package com.taskservice.repository;

import com.taskservice.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    public List<Task> findByAssignedUserId(Long userId);
}
