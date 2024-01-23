package com.sparta.schedule.repository;

import com.sparta.schedule.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}