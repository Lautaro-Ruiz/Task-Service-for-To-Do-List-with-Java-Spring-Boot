package Lautaro.Ruiz.TaskService.service;

import Lautaro.Ruiz.TaskService.entity.Task;
import Lautaro.Ruiz.TaskService.model.TaskPriority;
import Lautaro.Ruiz.TaskService.model.TaskStatus;

import java.util.List;

public interface TaskServiceInterface {
    List<Task> getAll();
    List<Task> getAllByStatus(TaskStatus status);
    List<Task> getAllByPriority(TaskPriority priority);
    Task getTask (long id);
    Task updateTask (Task task);
    Task deleteTask (long id);
    Task addTask(Task newTask);
}