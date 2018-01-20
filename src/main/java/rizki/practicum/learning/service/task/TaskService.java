package rizki.practicum.learning.service.task;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import rizki.practicum.learning.entity.Task;

import javax.annotation.Nullable;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Validated
public interface TaskService {
    Task updateTask(@Valid Task task);
    void deleteTask(@NotNull @NotBlank String idTask);
    Task getTask(@NotNull @NotBlank String idTask);

    Task addTask(@NotBlank String title, @NotNull String description,@Nullable Date startDate,
                 @NotNull Date endDate, @NotNull boolean allowLate, @NotNull @NotBlank String idUser,
                 @Nullable @NotBlank String idClassroom, @Nullable @NotBlank String idPracticum);

    Task addTask(Task task);

    List<Task> getTaskByPractican(@NotBlank String idPractican,@NotBlank String status);

    List<Task> getTask(@NotBlank @NotNull String mode, @NotBlank @NotNull String id, @NotBlank @NotNull String time);
}
