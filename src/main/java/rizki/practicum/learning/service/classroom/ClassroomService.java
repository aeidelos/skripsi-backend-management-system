package rizki.practicum.learning.service.classroom;

import rizki.practicum.learning.entity.Classroom;

import java.util.List;

public interface ClassroomService {
    Classroom addClassroom(Classroom classroom) throws Exception;
    Classroom updateNameClassroom(String idClassroom, String name) throws Exception;
    Classroom addAssistance(String idClassroom, String idAsssistance) throws Exception;
    boolean deleteClassroom(String idClassroom) throws Exception;
    List<Classroom> getAllClassroom() throws Exception;
    Classroom getClassroom(String idClassroom) throws Exception;
    Classroom enrollment(String enrollkey, String idUser) throws Exception;
}
