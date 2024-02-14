package Lautaro.Ruiz.TaskService.controller;

import Lautaro.Ruiz.TaskService.entity.Task;
import Lautaro.Ruiz.TaskService.model.TaskPriority;
import Lautaro.Ruiz.TaskService.model.TaskStatus;
import Lautaro.Ruiz.TaskService.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.validation.ObjectError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    /*Inyeccion del servicio mediante AutoWired en el controller.*/
    private final TaskService taskService;
    @Autowired
    public TaskController (TaskService taskService){
        this.taskService = taskService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Task>> getAll (){
        List<Task> tasks = taskService.getAll();
        if (tasks.isEmpty()) return ResponseEntity.noContent().header("X-Info","No se encontraron tareas.").build();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping ("/status")
    public ResponseEntity<List<Task>> getAllByStatus (@RequestParam(name = "status") TaskStatus status){
        if (!taskService.isValidStatus(status)) return ResponseEntity.badRequest().header("X-Info", "No existe dicho estado para filtrar tareas.").build();
        List<Task> tasks = taskService.getAllByStatus(status);
        if (!tasks.isEmpty()) return ResponseEntity.ok(tasks);
        return ResponseEntity.noContent().header("X-Info","No se encontraron tareas con ese estado.").build();
    }

    @GetMapping ("/priority")
    public ResponseEntity<List<Task>> getAllByPriority (@RequestParam (name="priority") TaskPriority priority){
        if (!taskService.isValidPriority(priority)) return ResponseEntity.badRequest().header("X-Info", "No existe dicha prioridad para filtrar tareas.").build();
        List <Task> tasks = taskService.getAllByPriority(priority);
        if (!tasks.isEmpty()) return ResponseEntity.ok(tasks);
        return ResponseEntity.noContent().header("X-Info","No se encontraron tareas con esa prioridad.").build();
    }

    @PostMapping("/")
    public ResponseEntity<?> addTask (@Valid @RequestBody Task newTask, BindingResult result){
        if (result.hasErrors()) {
            String errorMessage = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(errorMessage);
        }
        if (newTask == null) return ResponseEntity.badRequest().body("La tarea que intenta agregar no puede ser null");
        if (!taskService.isValidPriority(newTask.getPriority())) return ResponseEntity.badRequest().body("La tarea que intenta agregar no posee una prioridad valida.");
        if (!taskService.isValidStatus(newTask.getStatus())) return ResponseEntity.badRequest().body("La tarea que intenta agregar no posee un estado valido.");
        taskService.addTask(newTask);
        return ResponseEntity.ok().build();
    }

    @PutMapping ("/{taskId}") /*Llega el id del producto a cambiar y los datos que hay que cambiar en task.*/
    public ResponseEntity<Task> updateTask (@PathVariable("taskId") long id, @Valid @RequestBody Task task, BindingResult result){
        if (result.hasErrors()) {
            String errorMessage = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().header("X-Info", errorMessage).build();
        }
        task.setTaskId(id); /*A los datos que queremos actualizar que vienen en task, le seteamos el id del producto a cambiar.*/
        Task task_DB = taskService.updateTask(task); /*Llamado al metodo que se encarga de actualizar los campos.*/
        if(task_DB == null) return ResponseEntity.badRequest().header("X-Info", "No se hallaron resultados de la tarea que se intenta eliminar.").build();
        return ResponseEntity.ok(task_DB);
    }

    @DeleteMapping ("/{taskId}")
    public ResponseEntity<Task> deleteTask (@PathVariable ("taskId") long id){
        Task task_DB = taskService.deleteTask(id);
        if(task_DB == null) return ResponseEntity.badRequest().header("X-Info", "No se hallaron resultados de la tarea que se intenta eliminar.").build();
        return ResponseEntity.ok(task_DB);
    }
}