package rizki.practicum.learning.service.classroom;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import rizki.practicum.learning.entity.Classroom;
import rizki.practicum.learning.entity.User;
import rizki.practicum.learning.repository.ClassroomRepository;
import rizki.practicum.learning.service.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ClassroomServiceImpl implements ClassroomService {

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private UserService userService;

    @Override
    public Classroom addClassroom(Classroom classroom){
        Classroom duplicated = null;
        String enrollmentKey = null;
        do{
            enrollmentKey = generateEnrollmentKey();
            duplicated = classroomRepository.findByEnrollmentKey(enrollmentKey);
        }while(duplicated != null);
        classroom.setEnrollmentKey(enrollmentKey);
        return classroomRepository.save(classroom);
    }

    private String generateEnrollmentKey(){
        return RandomStringUtils.randomAlphanumeric(6);
    }

    @Override
    public Classroom updateClassroom(Classroom classroom){
        return classroomRepository.save(classroom);
    }


    @Override
    public Classroom addAssistance(String idClassroom, String idAsssistance){
        Classroom classroom = getClassroom(idClassroom);
        List<User> assistances = classroom.getAssistance();
        if(classroom.getAssistance().contains(userService.getUser(idAsssistance))){
            throw new DataIntegrityViolationException("Asisten sudah ada");
        }else{
            assistances.add(userService.getUser(idAsssistance));
            classroom.setAssistance(assistances);
            return classroomRepository.save(classroom);
        }
    }

    @Override
    public void deleteClassroom(String idClassroom) {
        classroomRepository.delete(idClassroom);
    }

    @Override
    public List<Classroom> getAllClassroom() throws Exception {
        return (ArrayList<Classroom>) classroomRepository.findAll();
    }

    @Override
    public Classroom getClassroom(String idClassroom) {
        return classroomRepository.findOne(idClassroom);
    }

    @Override
    public Classroom enrollment(String enrollkey, String idUser) throws Exception {
        Classroom classroom = classroomRepository.findByEnrollmentKey(enrollkey);
        List<User> practican = classroom.getPractican();
        practican.add(userService.getUser(idUser));
        return classroom;
    }

    @Override
    public List<Classroom> getByPracticum(String idPracticum) {
        return classroomRepository.findAllByPracticum(idPracticum);
    }
}
