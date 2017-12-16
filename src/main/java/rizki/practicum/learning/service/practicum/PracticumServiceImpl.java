package rizki.practicum.learning.service.practicum;

import org.springframework.beans.factory.annotation.Autowired;
import rizki.practicum.learning.entity.Classroom;
import rizki.practicum.learning.entity.Practicum;
import rizki.practicum.learning.entity.User;
import rizki.practicum.learning.repository.PracticumRepository;

import java.util.ArrayList;
import java.util.List;

public class PracticumServiceImpl implements PracticumService {

    @Autowired
    private PracticumRepository practicumRepository;

    @Override
    public Practicum addPracticum(Practicum practicum) throws Exception {
        return practicumRepository.save(practicum);
    }

    @Override
    public Practicum updatePracticum(Practicum practicum) throws Exception {
        return practicumRepository.save(practicum);
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
    public Practicum setCoordinatorAssistance(String idPracticum, User user) throws Exception {
        Practicum practicum = this.getDetailPracticum(idPracticum);
        practicum.setCoordinatorAssistance(user);
        return practicumRepository.save(practicum);
    }

    @Override
    public Practicum addNewClassroom(String idPracticum, Classroom classroom) throws Exception {
        Practicum practicum = this.getDetailPracticum(idPracticum);
        List<Classroom> classroomList = practicum.getClassrooms();
        classroomList.add(classroom);
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
}
