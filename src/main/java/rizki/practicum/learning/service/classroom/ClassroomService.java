package rizki.practicum.learning.service.classroom;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import rizki.practicum.learning.entity.Classroom;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface ClassroomService {
    Classroom addClassroom(@Valid Classroom classroom);
    Classroom updateClassroom(@Valid Classroom classroom);
    Classroom addAssistance(@NotBlank String idClassroom, @NotBlank String idAsssistance);
    void deleteClassroom(@NotBlank String idClassroom);
    Page<Classroom> getAllClassroom(Pageable pageable);
    Classroom getClassroom(@NotBlank String idClassroom);
    Classroom enrollment(String enrollkey, String idUser) throws Exception;
    List<Classroom> getByPracticum(@NotBlank String idPracticum);
    void enrollmentPractican(String enrollmentKey, String idUser) throws ClassNotFoundException;
    void unEnrollPractican(@NotBlank String idClassroom, @NotBlank String idPractican) throws ClassNotFoundException;
}
