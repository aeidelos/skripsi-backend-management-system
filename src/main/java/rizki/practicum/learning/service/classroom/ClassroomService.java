package rizki.practicum.learning.service.classroom;

import org.hibernate.validator.constraints.NotBlank;
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
    List<Classroom> getAllClassroom() throws Exception;
    Classroom getClassroom(@NotBlank String idClassroom);
    Classroom enrollment(String enrollkey, String idUser) throws Exception;
    List<Classroom> getByPracticum(@NotBlank String idPracticum);
}
