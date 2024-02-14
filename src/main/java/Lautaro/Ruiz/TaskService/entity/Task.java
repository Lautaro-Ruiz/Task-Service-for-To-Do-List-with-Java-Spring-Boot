package Lautaro.Ruiz.TaskService.entity;

import Lautaro.Ruiz.TaskService.model.TaskPriority;
import Lautaro.Ruiz.TaskService.model.TaskStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "tbl_Task")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long taskId;

    @NotNull(message = "El nombre de la tarea no puede ser nulo.")
    @NotBlank(message = "El nombre de la tarea no puede contener solo espacios en blanco.")
    @NotEmpty(message = "El nombre de la tarea no puede estar vacio.")
    private String name;

    private String description;

    @NotNull
    private TaskPriority priority;
    @NotNull
    private TaskStatus status;
    private boolean eliminated = false;
}
