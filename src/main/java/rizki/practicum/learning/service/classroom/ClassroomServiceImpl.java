package rizki.practicum.learning.service.classroom;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import rizki.practicum.learning.entity.Classroom;
import rizki.practicum.learning.entity.Role;
import rizki.practicum.learning.entity.User;
import rizki.practicum.learning.repository.ClassroomRepository;
import rizki.practicum.learning.service.role.RoleService;
import rizki.practicum.learning.service.user.UserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ClassroomServiceImpl implements ClassroomService {

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Override
    public Classroom addClassroom(Classroom classroom){
        Classroom duplicated = null;
        String enrollmentKey = null;
        do{
            enrollmentKey = generateEnrollmentKey(); //generate key
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
        // updte classroom
        Classroom old = classroomRepository.findOne(classroom.getId());
        // check if assistant is updated
        if (classroom.getAssistance().hashCode() != old.getAssistance().hashCode()) {
            List<User> newClass = classroom.getAssistance();
            List<User> oldClass = old.getAssistance();
            List<User> removed = oldClass;
            List<User> added = newClass;
            removed.removeAll(newClass);
            added.removeAll(oldClass);
            Role role = roleService.getRole("asprak");
            removed.stream().forEach(user -> {
                // removed state of assistant
                List<Classroom> classrooms = classroomRepository.findAllByAssistanceContains(user);
                if (classrooms.size() == 1) {
                    List<Role> tempRole = user.getRole();
                    tempRole.removeAll(Collections.singleton(role));
                    user.setRole(tempRole);
                    userService.updateUser(user);
                }
            });
            added.stream().forEach(user -> {
                // add new assistant role
                List<Classroom> classrooms = classroomRepository.findAllByAssistanceContains(user);
                if(classrooms.size() == 0) {
                    List<Role> tempRole = user.getRole();
                    if(!tempRole.contains(role)) {
                        tempRole.add(role);
                        user.setRole(tempRole);
                        userService.updateUser(user);
                    }
                }
            });
        }
        return classroomRepository.save(classroom);
    }

    @Override
    public Classroom addAssistance(String idClassroom, String idAsssistance){
        Classroom classroom = getClassroom(idClassroom);
        List<User> assistances = classroom.getAssistance();
        if(classroom.getAssistance().contains(userService.getUser(idAsssistance))){
            throw new DataIntegrityViolationException("Asisten sudah ada");
        }else{
            User user = userService.getUser(idAsssistance);
            assistances.add(user);
            classroom.setAssistance(assistances);
            List<Role> roles = user.getRole();
            Role role = roleService.getRole("asprak");
            if (!roles.contains(role)) {
                roles.add(role);
                user.setRole(roles);
            }
            userService.updateUser(user);
            return classroomRepository.save(classroom);
        }
    }

    @Override
    public void deleteClassroom(String idClassroom) {
        Classroom classroom = classroomRepository.findOne(idClassroom);
        List<User> oldClass = classroom.getAssistance();
        Role role = roleService.getRole("asprak");
        // remove current state of assistant based on classroom
        oldClass.stream().forEach(user -> {
            List<Classroom> classrooms = classroomRepository.findAllByAssistanceContains(user);
            if (classrooms.size() == 1) {
                List<Role> tempRole = user.getRole();
                tempRole.remove(role);
                user.setRole(tempRole);
                userService.updateUser(user);
            }
        });
        classroomRepository.delete(idClassroom);
    }

    @Override
    public Page<Classroom> getAllClassroom(Pageable pageable){
        return classroomRepository.findAll(pageable);
    }

    @Override
    public Classroom getClassroom(String idClassroom) {
        return classroomRepository.findOne(idClassroom);
    }

    @Override
    public List<Classroom> getByPracticum(String idPracticum) {
        return classroomRepository.findAllByPracticum(idPracticum);
    }

    @Override
    public void enrollmentPractican(String enrollmentKey, String idUser) throws ClassNotFoundException{
        Classroom classroom = classroomRepository.findByEnrollmentKey(enrollmentKey);
        if(classroom == null) throw new ClassNotFoundException("Classroom Not Found");
        List<User> practicans = classroom.getPractican();
        User user = userService.getUser(idUser);
        if(user == null) throw new ClassNotFoundException("User not Found");
        practicans.add(user);
        classroom.setPractican(practicans);
    }

    @Override
    public void unEnrollPractican(String idClassroom, String idPractican) throws ClassNotFoundException {
        Classroom classroom = classroomRepository.findOne(idClassroom);
        if(classroom == null) throw new ClassNotFoundException("Classroom Not Found");
        List<User> practicans = classroom.getPractican();
        User user = userService.getUser(idPractican);
        if(user == null) throw new ClassNotFoundException("User not Found");
        practicans.remove(user);
        classroom.setPractican(practicans);
        classroomRepository.save(classroom);
    }

    @Override
    public List<Classroom> getByAssistance(String idUser) {
        return classroomRepository.findAllByAssistanceContains(userService.getUser(idUser));
    }

    @Override
    public List<Classroom> getByPractican(String idUser) {
        return classroomRepository.findAllByPracticanContains(userService.getUser(idUser));
    }

    @Override
    public Classroom searchByEnrollmentKey(String enrollmentKey, String idUser) {
        User user = userService.getUser(idUser);
        Classroom classroom = classroomRepository.findByEnrollmentKey(enrollmentKey);
        if (classroom == null) throw new ResourceNotFoundException("Kelas tidak ditemukan");
        else if (classroom.getPractican().contains(user)) throw new DataIntegrityViolationException("Pengguna sudah terdaftar sebagai praktikan");
        else if (classroom.getAssistance().contains(user)) throw new DataIntegrityViolationException("Pengguna sudah terdaftar sebagai asisten");
        else return classroom;
    }
}
