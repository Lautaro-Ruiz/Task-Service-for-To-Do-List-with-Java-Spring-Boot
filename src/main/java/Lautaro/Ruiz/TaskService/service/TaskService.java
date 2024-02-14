package Lautaro.Ruiz.TaskService.service;

import Lautaro.Ruiz.TaskService.entity.Task;
import Lautaro.Ruiz.TaskService.model.TaskPriority;
import Lautaro.Ruiz.TaskService.model.TaskStatus;
import Lautaro.Ruiz.TaskService.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService implements TaskServiceInterface {

    /*Inyeccion del repositorio mediante AutoWired en el service.*/
    private final TaskRepository taskRepository;
    @Autowired
    public TaskService (TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> getAllByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }

    @Override
    public List<Task> getAllByPriority(TaskPriority priority) {
        return taskRepository.findByPriority (priority);
    }

    @Override
    public Task getTask (long id){
        return taskRepository.findById(id).orElse(null);
    }

    @Override
    public Task updateTask (Task task){
        Task task_DB = getTask (task.getTaskId());
        if (task_DB == null)
            return null;
        task_DB.setName(task.getName());
        task_DB.setDescription(task.getDescription());task_DB.setPriority(task.getPriority());
        task_DB.setEliminated(task.isEliminated());
        return taskRepository.save(task_DB);
    }

    @Override
    public Task deleteTask (long id){
        Task task_DB = getTask(id);
        if (task_DB == null) return null;
        task_DB.setEliminated(true);
        return taskRepository.save(task_DB);
    }

    @Override
    public void addTask(Task newTask) {
        taskRepository.save(newTask);
    }

    public boolean isValidPriority (TaskPriority priority) {
        return (priority == TaskPriority.HIGH || priority == TaskPriority.MEDIUM || priority == TaskPriority.LOW);
    }

    public boolean isValidStatus (TaskStatus status) {
        return (status == TaskStatus.TO_DO || status == TaskStatus.IN_PROGRESS || status == TaskStatus.DONE);
    }
}