package rizki.practicum.learning.service.classroom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rizki.practicum.learning.entity.Classroom;
import rizki.practicum.learning.entity.User;
import rizki.practicum.learning.repository.ClassroomRepository;
import rizki.practicum.learning.service.user.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClassroomServiceImpl implements ClassroomService {

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private UserService userService;

    @Override
    public Classroom addClassroom(Classroom classroom) throws Exception {
        return classroomRepository.save(classroom);
    }

    @Override
    public Classroom updateNameClassroom(String idClassroom, String name) throws Exception {
        Classroom classroom = getClassroom(idClassroom);
        classroom.setName(name);
        return classroomRepository.save(classroom);
    }

    @Override
    public Classroom addAssistance(String idClassroom, String idAsssistance) throws Exception {
        Classroom classroom = getClassroom(idClassroom);
        List<User> assistances = classroom.getAssistance();
        assistances.add(userService.getUser(idAsssistance));
        classroom.setAssistance(assistances);
        return classroomRepository.save(classroom);
    }

    @Override
    public boolean deleteClassroom(String idClassroom) throws Exception {
        classroomRepository.delete(idClassroom);
        if(classroomRepository.findOne(idClassroom)!=null){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public List<Classroom> getAllClassroom() throws Exception {
        return (ArrayList<Classroom>) classroomRepository.findAll();
    }

    @Override
    public Classroom getClassroom(String idClassroom) throws Exception {
        return classroomRepository.findOne(idClassroom);
    }

    @Override
    public Classroom enrollment(String enrollkey, String idUser) throws Exception {
        Classroom classroom = classroomRepository.findByEnrollmentKey(enrollkey);
        List<User> practican = classroom.getPractican();
        practican.add(userService.getUser(idUser));
        return classroom;
    }
}
