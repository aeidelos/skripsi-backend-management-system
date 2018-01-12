package rizki.practicum.learning.service.task;

import rizki.practicum.learning.entity.Task;

import java.util.List;

public interface TaskService {
    Task addTask(Task task) throws Exception;
    Task updateTask(Task task) throws Exception;
    boolean deleteTask(String idTask) throws Exception;
    Task getTask(String idTask) throws Exception;
    List<Task> getTaskByClassroom(String idClassroom) throws Exception;
    List<Task> getTaskByPracticum(String idPracticum) throws Exception;
    List<Task> getTaskByCreator(String idCreator) throws Exception;
    List<Task> getTask() throws Exception;
}
