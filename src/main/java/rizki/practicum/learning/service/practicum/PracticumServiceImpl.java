package rizki.practicum.learning.service.practicum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rizki.practicum.learning.entity.Classroom;
import rizki.practicum.learning.entity.Practicum;
import rizki.practicum.learning.entity.User;
import rizki.practicum.learning.repository.PracticumRepository;
import rizki.practicum.learning.service.user.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class PracticumServiceImpl implements PracticumService {

    @Autowired
    private PracticumRepository practicumRepository;

    @Autowired
    private UserService userService;

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
        return practicumRepository.save(practicum);
    }

    @Override
    public void deletePracticum(Practicum practicum) {
        practicumRepository.delete(practicum);
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
