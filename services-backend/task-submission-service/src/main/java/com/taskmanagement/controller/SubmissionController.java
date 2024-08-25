package com.taskmanagement.controller;

import com.taskmanagement.dto.UserDto;
import com.taskmanagement.model.Submission;
import com.taskmanagement.service.SubmissionService;
import com.taskmanagement.service.TaskService;
import com.taskmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;
    @Autowired
    private UserService userService;
    @Autowired
    private TaskService taskService;

    @PostMapping()
    public ResponseEntity<Submission> submitTask(
            @RequestParam Long task_id,
            @RequestParam String github_link,
            @RequestHeader("Authorization") String jwt) throws Exception{

        UserDto user = userService.getUserProfile(jwt);
        Submission submission = submissionService.submitTask(task_id, github_link, user.getId(), jwt);
        return new ResponseEntity<>(submission, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Submission> getSubmissionById(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt) throws Exception{

        UserDto user = userService.getUserProfile(jwt);
        Submission submission = submissionService.getTaskSubmissionById(id);
        return new ResponseEntity<>(submission, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<Submission>> getAllSubmissions(
            @RequestHeader("Authorization") String jwt) throws Exception{

        UserDto user = userService.getUserProfile(jwt);
        List<Submission> submissions = submissionService.getAllSubmissions();
        return new ResponseEntity<>(submissions, HttpStatus.OK);
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<Submission>> getTaskSubmissionsByTaskId(
            @PathVariable Long taskId,
            @RequestHeader("Authorization") String jwt) throws Exception{

        UserDto user = userService.getUserProfile(jwt);
        List<Submission> submissions = submissionService.getTaskSubmissionsByTaskId(taskId);
        return new ResponseEntity<>(submissions, HttpStatus.OK);
    }

    @PutMapping("/accept-or-decline/{id}")
    public ResponseEntity<Submission> acceptOrDeclineSubmission(
            @PathVariable Long id,
            @RequestParam("status") String status,
            @RequestHeader("Authorization") String jwt) throws Exception{

        UserDto user = userService.getUserProfile(jwt);
        Submission submission = submissionService.acceptOrDeclineSubmission(id,status);

        return new ResponseEntity<>(submission, HttpStatus.OK);
    }
}

