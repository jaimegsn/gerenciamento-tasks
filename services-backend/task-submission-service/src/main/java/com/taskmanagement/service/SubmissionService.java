package com.taskmanagement.service;

import com.taskmanagement.model.Submission;

import java.util.List;

public interface SubmissionService {
    Submission submitTask(Long taskId, String githubLink, Long userId, String jwt) throws Exception;

    Submission getTaskSubmissionById(Long submissionId) throws Exception;

    List<Submission> getAllSubmissions();

    List<Submission> getTaskSubmissionsByTaskId(Long taskId);

    Submission acceptOrDeclineSubmission(Long id, String status) throws Exception;
}
