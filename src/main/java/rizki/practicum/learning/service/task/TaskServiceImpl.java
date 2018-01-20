package rizki.practicum.learning.service.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rizki.practicum.learning.entity.Classroom;
import rizki.practicum.learning.entity.Practicum;
import rizki.practicum.learning.entity.Task;
import rizki.practicum.learning.entity.User;
import rizki.practicum.learning.repository.ClassroomRepository;
import rizki.practicum.learning.repository.PracticumRepository;
import rizki.practicum.learning.repository.TaskRepository;
import rizki.practicum.learning.repository.UserRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private PracticumRepository practicumRepository;

    @Override
    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(String idTask){
        taskRepository.delete(idTask);
    }

    @Override
    public Task getTask(String idTask){
        return taskRepository.findOne(idTask);
    }

    @Override
    public Task addTask(String title, String description, Date startDate, Date endDate, boolean allowLate, String idUser, String idClassroom, String idPracticum) {

        User creator = userRepository.findOne(idUser);
        Practicum practicum = null;
        Classroom classroom = null;
        if(idPracticum!=null || idPracticum!="null"){
            practicum = practicumRepository.findOne(idPracticum);
        }else if(idClassroom != null || idClassroom != null){
            classroom = classroomRepository.findOne(idClassroom);
        }
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setCreatedDate(startDate);
        task.setDueDate(endDate);
        task.setAllowLate(allowLate);
        task.setCreatedBy(creator);
        task.setClassroom(classroom);
        task.setPracticum(practicum);

        return taskRepository.save(task);
    }

    @Override
    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public List<Task> getTaskByPractican(String idPractican, String status) {
        List<Task> tasks = new ArrayList<>();
        List<Task> byPracticums;
        List<Task> byClassrooms;
        if(status.equalsIgnoreCase("past")){
            byPracticums = taskRepository.findAllPastByPracticumOfPractican(idPractican, new Date());
            byClassrooms = taskRepository.findAllPastByClassroomOfPractican(idPractican,new Date());
        }else{
            byPracticums = taskRepository.findAllByPracticumOfPractican(idPractican, new Date());
            byClassrooms = taskRepository.findAllByClassroomOfPractican(idPractican, new Date());
        }
        if(byPracticums != null) tasks.addAll(byPracticums);
        if(byClassrooms != null) tasks.addAll(byClassrooms);
        return tasks;
    }

    @Override
    public List<Task> getTask(String mode, String id, String time) {
        List<Task> tasks = null;
        Date date = new Date();
        if(mode.equalsIgnoreCase("practicum")){
            Practicum practicum = practicumRepository.findOne(id);
            if(time.equalsIgnoreCase("past")){
                tasks = taskRepository.findAllByPracticumAndDueDateIsBefore(practicum,date);
            }else{
                tasks = taskRepository.findAllByPracticumAndDueDateIsAfter(practicum,date);
            }
        }else if(mode.equalsIgnoreCase("classroom")){
            Classroom classroom = classroomRepository.findOne(id);
            if(time.equalsIgnoreCase("past")){
                tasks = taskRepository.findAllByClassroomAndDueDateIsAfter(classroom, date);
            }else{
                tasks = taskRepository.findAllByClassroomAndDueDateIsBefore(classroom, date);
            }
        }else if(mode.equalsIgnoreCase("mix")){
            Classroom classroom = classroomRepository.findOne(id);
            if(time.equalsIgnoreCase("past")){
                tasks = taskRepository.findAllByClassroomAndDueDateIsAfter(classroom, date);
                tasks.addAll(taskRepository.findAllByPracticumAndDueDateIsAfter(classroom.getPracticum(),date));
            }else{
                tasks = taskRepository.findAllByClassroomAndDueDateIsBefore(classroom, date);
                tasks.addAll(taskRepository.findAllByPracticumAndDueDateIsBefore(classroom.getPracticum(),date));
            }
        }else{
            throw new IllegalArgumentException();
        }
        return tasks;
    }
}
