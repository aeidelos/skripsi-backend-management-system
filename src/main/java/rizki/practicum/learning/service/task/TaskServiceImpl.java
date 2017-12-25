package rizki.practicum.learning.service.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rizki.practicum.learning.entity.Task;
import rizki.practicum.learning.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Task addTask(Task task) throws Exception {
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Task task) throws Exception {
        return taskRepository.save(task);
    }

    @Override
    public boolean deleteTask(String idTask) throws Exception {
        taskRepository.delete(idTask);
        return getTask(idTask)==null;
    }

    @Override
    public Task getTask(String idTask) throws Exception {
        return taskRepository.findOne(idTask);
    }

    @Override
    public List<Task> getTaskByClassroom(String idClassroom) throws Exception {
        return (ArrayList<Task>) taskRepository.findAllByClassroom(idClassroom);
    }

    @Override
    public List<Task> getTaskByPracticum(String idPracticum) throws Exception {
        return (ArrayList<Task>) taskRepository.findAllByPracticum(idPracticum);
    }

    @Override
    public List<Task> getTaskByCreator(String idCreator) throws Exception {
        return (ArrayList<Task>) taskRepository.findAllByCreatedBy(idCreator);
    }
}
