package rizki.practicum.learning.service.practicum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rizki.practicum.learning.entity.Classroom;
import rizki.practicum.learning.entity.Practicum;
import rizki.practicum.learning.entity.Role;
import rizki.practicum.learning.entity.User;
import rizki.practicum.learning.repository.PracticumRepository;
import rizki.practicum.learning.service.role.RoleDefinition;
import rizki.practicum.learning.service.role.RoleService;
import rizki.practicum.learning.service.user.UserService;

import java.util.List;

@Service
public class PracticumServiceImpl implements PracticumService {

    @Autowired
    private PracticumRepository practicumRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Override
    public Practicum addPracticum(Practicum practicum){
        return practicumRepository.save(practicum);
    }

    @Override
    public Practicum getPracticum(String idPracticum) {
        return practicumRepository.findOne(idPracticum);
    }

    @Override
    public Practicum updatePracticum(Practicum practicum){
        Practicum old = practicumRepository.findOne(practicum.getId());
        Role role = roleService.getRole("koas");
        if(old.getCoordinatorAssistance() != practicum.getCoordinatorAssistance()) {
            User oldCoordinator = old.getCoordinatorAssistance();
            if (oldCoordinator != null ) {
                List<Role> tempRole = oldCoordinator.getRole();
                tempRole.remove(role);
                oldCoordinator.setRole(tempRole);
                userService.updateUser(oldCoordinator);
            }
            User newCoordinator = practicum.getCoordinatorAssistance();
            if (newCoordinator != null ) {
                List<Role> tempRole = newCoordinator.getRole();
                if(!tempRole.contains(role)) {
                    tempRole.add(role);
                    newCoordinator.setRole(tempRole);
                    userService.updateUser(newCoordinator);
                }
            }
        }
        return practicumRepository.save(practicum);
    }

    @Override
    public void deletePracticum(Practicum practicum) {
        User oldCoordinator = practicum.getCoordinatorAssistance();
        if (oldCoordinator != null ) {
            Role role = roleService.getRole("koas");
            List<Role> tempRole = oldCoordinator.getRole();
            tempRole.remove(role);
            oldCoordinator.setRole(tempRole);
            userService.updateUser(oldCoordinator);
        }
        practicumRepository.delete(practicum);
    }

    @Override
    public Practicum getPracticumByCoordinatorAssistance(String idUser) {
        User user = userService.getUser(idUser);
        List<Role> roles = user.getRole();
        for (Role role : roles){
            if(role.getInitial().equalsIgnoreCase(RoleDefinition.CoordinatorAssistance.initial)){
                return practicumRepository.findByCoordinatorAssistance(user);
            }
        }
        return null;
    }

    @Override
    public Practicum deactivatePracticum(String idPracticum) throws Exception {
        return this.changePracticumActivation(idPracticum,false);
    }

    @Override
    public Practicum reactivatePracticum(String idPracticum) throws Exception {
        return this.changePracticumActivation(idPracticum,true);
    }

    private Practicum changePracticumActivation(String idPracticum, boolean status) throws Exception{
        Practicum practicum = this.getDetailPracticum(idPracticum);
        practicum.setActive(true);
        return practicumRepository.save(practicum);
    }

    @Override
    public Practicum setCoordinatorAssistance(String idPracticum, String user) throws Exception {
        Practicum practicum = this.getDetailPracticum(idPracticum);
        practicum.setCoordinatorAssistance(userService.getUser(user));
        return practicumRepository.save(practicum);
    }

    @Override
    public Practicum addNewClassroom(String idPracticum, Classroom classroom) throws Exception {
        Practicum practicum = this.getDetailPracticum(idPracticum);
        return practicumRepository.save(practicum);
    }

    @Override
    public List<Practicum> getListPracticum() throws Exception {
        return (List<Practicum>) practicumRepository.findAll();
    }

    @Override
    public Practicum getDetailPracticum(String idPracticum) throws Exception {
        return practicumRepository.findOne(idPracticum);
    }

    @Override
    public Page<Practicum> getAllPracticum(Pageable pageable) {
        return practicumRepository.findAll(pageable);
    }
}
