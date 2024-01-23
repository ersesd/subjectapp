package com.sparta.schedule.controller;

import com.sparta.schedule.dto.TaskCreateDto;
import com.sparta.schedule.model.Task;
import com.sparta.schedule.service.TaskService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Date;


import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    // ...
    @GetMapping("/{id}")
    public TaskDto getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id).orElse(null);

        // Check if the task exists
        if (task != null) {
            // Convert the entity to DTO, excluding password field
            return new TaskDto(task);
        } else {
            // Handle case where the task with the given ID is not found
            // For simplicity, returning null here; you might want to handle this differently
            return null;
        }
    }

    @GetMapping("/ids")
    public List<TaskDto> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();

        // Convert the list of entities to a list of DTOs, excluding password field
        List<TaskDto> taskDtos = tasks.stream()
                .map(TaskDto::new)
                .collect(Collectors.toList());

        // Sort the list in descending order based on creation date
        taskDtos.sort(Comparator.comparing(TaskDto::getCreationDate).reversed());

        return taskDtos;
    }


    // ...

    // Task DTO class to control what fields are included in the response
    private static class TaskDto {
        @Getter
        @Setter
        private Long id;
        private String title;
        private String content;
        private String personInCharge;
        private Date creationDate;

        public TaskDto(Task task) {
            this.id = task.getId();
            this.title = task.getTitle();
            this.content = task.getContent();
            this.personInCharge = task.getPersonInCharge();
            this.creationDate = task.getCreationDate();
        }

        // getters

        public Date getCreationDate() {
            return creationDate;
        }


        public Object getTitle() {
            return title;
        }


    }

    @PostMapping
    public TaskDto createTask(@RequestBody TaskCreateDto taskCreateDto) {
        // Convert DTO to entity
        Task newTask = new Task();
        newTask.setTitle(taskCreateDto.getTitle());
        newTask.setContent(taskCreateDto.getContent());
        newTask.setPersonInCharge(taskCreateDto.getPersonInCharge());
        // Set other fields as needed

        // Save the new task to the database
        Task savedTask = taskService.saveTask(newTask);

        // Convert the saved entity to DTO and return
        return new TaskDto(savedTask);
    }


}