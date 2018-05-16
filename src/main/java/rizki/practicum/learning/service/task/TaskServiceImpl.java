package rizki.practicum.learning.service.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rizki.practicum.learning.entity.*;
import rizki.practicum.learning.repository.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

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

    @Autowired
    private AssignmentRepository assignmentRepository;

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
        practicum = practicumRepository.findOne(idPracticum);
        classroom = classroomRepository.findOne(idClassroom);
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
    public Task updateTask(Task task) {
        for(Assignment assignment: task.getAssignments()) {
            List<Assignment> result = task.getAssignments();
            if(assignment.getId()==null) {
                result.remove(assignment);
                result.add(assignmentRepository.save(assignment));
                task.setAssignments(result);
            }
        }
        return taskRepository.save(task);
    }

    @Override
    public Task addTask(Task task) {
        if(task.getAssignments()!=null && task.getAssignments().size() > 0) {
            List<Assignment> result = new ArrayList<>();
            for(Assignment assignment : task.getAssignments()) {
                result.add(assignmentRepository.save(assignment));
            }
            task.setAssignments(result);
        }
        return taskRepository.save(task);
    }

    @Override
    public List<Task> getTaskByPractican(String idPractican, String status) {
        List<Task> tasks = new ArrayList<>();
        List<Task> byPracticums = null;
        List<Task> byClassrooms = null;
        List<Classroom> classrooms = classroomRepository.findAllByPracticanContains
                                        (userRepository.findOne(idPractican));
        if(status.equalsIgnoreCase("past")){
            byClassrooms = taskRepository.findAllByClassroomInAndDueDateIsBefore(classrooms, new Date());
            byPracticums = taskRepository.findAllByPracticumInAndDueDateIsBefore(classrooms
                    .stream()
                    .map(Classroom::getPracticum)
                    .collect(Collectors.toList()), new Date());
        }else{
            byClassrooms = taskRepository.findAllByClassroomInAndDueDateIsAfter(classrooms, new Date());
            byPracticums = taskRepository.findAllByPracticumInAndDueDateIsAfter(classrooms
                    .stream()
                    .map(Classroom::getPracticum)
                    .collect(Collectors.toList()), new Date());
        }
        if(byPracticums != null) tasks.addAll(byPracticums);
        if(byClassrooms != null) tasks.addAll(byClassrooms);
        return tasks;
    }

    @Override
    public List<Task> getTask(String mode, String id, String time) {
        AtomicReference<List<Task>> tasks = new AtomicReference<>();
        Date date = new Date();
        if(mode.equalsIgnoreCase("practicum")){
            Practicum practicum = practicumRepository.findOne(id);
            if(time.equalsIgnoreCase("past")){
                tasks.set(taskRepository.findAllByPracticumAndDueDateIsBefore(practicum, date));
            }else{
                tasks.set(taskRepository.findAllByPracticumAndDueDateIsAfter(practicum, date));
            }
        }else if(mode.equalsIgnoreCase("classroom")){
            Classroom classroom = classroomRepository.findOne(id);
            if(time.equalsIgnoreCase("past")){
                tasks.set(taskRepository.findAllByClassroomAndDueDateIsBefore(classroom, date));
            }else{
                tasks.set(taskRepository.findAllByClassroomAndDueDateIsAfter(classroom, date));
            }
        }else if(mode.equalsIgnoreCase("mix")){
            Classroom classroom = classroomRepository.findOne(id);
            if(time.equalsIgnoreCase("past")){
                tasks.set(taskRepository.findAllByClassroomAndDueDateIsBefore(classroom, date));
                tasks.get().addAll(taskRepository.findAllByPracticumAndDueDateIsBefore(classroom.getPracticum(),date));
            } else if(time.equalsIgnoreCase("now")){
                List<Classroom> classrooms = classroomRepository.findAllByPracticanContains(userRepository.findOne(id));
                classrooms.stream().forEach(cl-> {
                    List<Task> temp;
                    if(tasks.get() == null) {
                        temp = new ArrayList<>();
                    }else {
                        temp = tasks.get();
                    }
                    temp.addAll(taskRepository.findAllByClassroomAndDueDateIsAfter(cl, date));
                    temp.addAll(taskRepository.findAllByPracticumAndDueDateIsAfter(cl.getPracticum(), date));
                    tasks.set(temp);
                });
                List<Task> ta = tasks.get();
                if(ta != null)
                ta = tasks.get().stream().filter(task -> task.getDueDate().compareTo(date) != 0 &&
                        task.getDueDate().compareTo(date) != 1 ).collect(Collectors.toList());
                tasks.set(ta);
            }else{
                tasks.set(taskRepository.findAllByClassroomAndDueDateIsAfter(classroom, date));
                tasks.get().addAll(taskRepository.findAllByPracticumAndDueDateIsAfter(classroom.getPracticum(),date));
            }
        }else{
            throw new IllegalArgumentException("Kategori tidak ditemukan");
        }
        return tasks.get();
    }
}
