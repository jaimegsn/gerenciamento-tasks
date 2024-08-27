package com.taskservice.service;

import com.taskservice.model.Task;
import com.taskservice.model.TaskStatus;
import com.taskservice.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImplementation implements TaskService{
    @Autowired
    TaskRepository taskRepository;

    @Override
    public Task createTask(Task task, String requesterRole) throws Exception {
        if(!requesterRole.equals("ROLE_ADMIN")){
            throw new Exception("Only admin can create a task");
        }
        task.setStatus(TaskStatus.PENDING);
        task.setCreatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    @Override
    public Task getTaskById(Long id) throws Exception {
        return taskRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Task not found" + id)
        );
    }

    @Override
    public List<Task> getAllTask(TaskStatus status) {
        List<Task> tasks = taskRepository.findAll();

        List<Task> filteredTasks = tasks.stream().filter(
                task -> status==null || task.getStatus().name().equalsIgnoreCase(status.toString())
        ).toList();

        return filteredTasks;
    }

    @Override
    public Task updateTask(Long id, Task updatedTask, Long userId) throws Exception {
        Task existingTask = getTaskById(id);

        // Definir os campos que queremos atualizar
        List<String> updateFields = Arrays.asList("title", "image", "description", "status", "deadLine");

        //Obtem todos os campos da classe Task
        Field[] fields = Task.class.getDeclaredFields();

        for (Field field : fields){
            if (updateFields.contains(field.getName())) {
                field.setAccessible(true); // Permitir acesso a campos privados

                try {
                    // Obtem o valor do campo no objeto updatedTask
                    Object value = field.get(updatedTask);

                    if (value != null) {
                        field.set(existingTask, value);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return taskRepository.save(existingTask);
    }

    @Override
    public void deleteTask(Long id) throws Exception {
        getTaskById(id);
        taskRepository.deleteById(id);
    }

    @Override
    public Task assignedToUser(Long userId, Long taskId) throws Exception {
        Task task = getTaskById(taskId);
        task.setAssignedUserId(userId);
        //task.setStatus(TaskStatus.DONE);

        return taskRepository.save(task);
    }

    @Override
    public List<Task> assignedUserTasks(Long userId, TaskStatus status) {
        List<Task> allTaskAssigned = taskRepository.findByAssignedUserId(userId);

        List<Task> filteredTasks = allTaskAssigned.stream().filter(
                task -> status==null || task.getStatus().name().equalsIgnoreCase(status.toString())
        ).toList();

        return filteredTasks;
    }

    @Override
    public Task completeTask(Long taskId) throws Exception {
        Task task = getTaskById(taskId);
        task.setStatus(TaskStatus.DONE);

        return taskRepository.save(task);
    }
}
