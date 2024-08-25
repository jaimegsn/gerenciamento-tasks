package com.taskservice.controller;

import com.taskservice.dto.UserDTO;
import com.taskservice.model.Task;
import com.taskservice.model.TaskStatus;
import com.taskservice.service.TaskService;
import com.taskservice.service.UserService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task,
                                           @RequestHeader("Authorization") String jwt) throws Exception {

        UserDTO userDTO = userService.getUserProfile(jwt);
        Task createdTask = taskService.createTask(task, userDTO.getRole());

        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id,
                                           @RequestHeader("Authorization") String jwt) throws Exception {

        UserDTO userDTO = userService.getUserProfile(jwt);
        Task task = taskService.getTaskById(id);

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Task>> getAssignedUsersTask(
            @RequestParam(required = false)TaskStatus status,
            @RequestHeader("Authorization") String jwt) throws Exception {

        UserDTO userDTO = userService.getUserProfile(jwt);
        List<Task> tasks = taskService.assignedUserTasks(userDTO.getId(), status);

        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<Task>> getAllTask(
            @RequestParam(required = false)TaskStatus status,
            @RequestHeader("Authorization") String jwt) throws Exception {

        UserDTO userDTO = userService.getUserProfile(jwt);
        List<Task> tasks = taskService.getAllTask(status);

        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PutMapping("/{id}/user/{userId}/assigned")
    public ResponseEntity<Task> assignTaskToUser(
            @PathVariable Long id,
            @PathVariable Long userId,
            @RequestHeader("Authorization") String jwt) throws Exception {

        UserDTO userDTO = userService.getUserProfile(jwt);
        Task task = taskService.assignedToUser(userId,id);

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Long id,
            @RequestBody Task task,
            @RequestHeader("Authorization") String jwt) throws Exception {

        UserDTO userDTO = userService.getUserProfile(jwt);
        Task taskUpdated = taskService.updateTask(id, task, userDTO.getId());

        return new ResponseEntity<>(taskUpdated, HttpStatus.OK);
    }

    @PutMapping("/complete-task/{id}")
    public ResponseEntity<Task> completeTask(@PathVariable Long id) throws Exception {

        Task taskUpdated = taskService.completeTask(id);
        return new ResponseEntity<>(taskUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) throws Exception {

        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
