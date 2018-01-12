package rizki.practicum.learning.service.practicum;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rizki.practicum.learning.entity.Classroom;
import rizki.practicum.learning.entity.Practicum;
import rizki.practicum.learning.entity.User;

import java.util.List;

public interface PracticumService {
    Practicum deactivatePracticum(String idPracticum) throws Exception;
    Practicum reactivatePracticum(String idPracticum) throws Exception;
    Practicum setCoordinatorAssistance(String idPracticum, String idUser) throws Exception;
    Practicum addNewClassroom(String idPracticum, Classroom classroom) throws Exception;
    List<Practicum> getListPracticum() throws Exception;
    Practicum getDetailPracticum(String idPracticum) throws Exception;

    Page<Practicum> getAllPracticum(Pageable pageable);
    Practicum addPracticum(Practicum practicum);
    Practicum getPracticum(String idPracticum);
    Practicum updatePracticum(Practicum practicum);
    void deletePracticum(Practicum practicum);
}
