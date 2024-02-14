package Lautaro.Ruiz.TaskService.repository;

import Lautaro.Ruiz.TaskService.entity.Task;
import Lautaro.Ruiz.TaskService.model.TaskPriority;
import Lautaro.Ruiz.TaskService.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findByStatus (TaskStatus taskStatus);
    List<Task> findByPriority(TaskPriority priority);
}