package rizki.practicum.learning.service.practicum;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import rizki.practicum.learning.entity.Classroom;
import rizki.practicum.learning.entity.Practicum;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface PracticumService {
    Practicum deactivatePracticum(String idPracticum) throws Exception;
    Practicum reactivatePracticum(String idPracticum) throws Exception;
    Practicum setCoordinatorAssistance(String idPracticum, String idUser) throws Exception;
    Practicum addNewClassroom(String idPracticum, Classroom classroom) throws Exception;
    List<Practicum> getListPracticum() throws Exception;

    Practicum getDetailPracticum(@NotBlank String idPracticum) throws Exception;
    Page<Practicum> getAllPracticum(Pageable pageable);
    Practicum addPracticum(@Valid Practicum practicum);
    Practicum getPracticum(@NotBlank String idPracticum);
    Practicum updatePracticum(@Valid Practicum practicum);
    void deletePracticum(@Valid Practicum practicum);

    Practicum getPracticumByCoordinatorAssistance(@NotBlank String idUser);
}
