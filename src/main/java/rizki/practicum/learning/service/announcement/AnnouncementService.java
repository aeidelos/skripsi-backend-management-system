package rizki.practicum.learning.service.announcement;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import rizki.practicum.learning.entity.Announcement;

import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface AnnouncementService {
    Announcement save(Announcement announcement);

    Object get(String idClassroom, String idPracticum, String idUser);

    void delete(Announcement announcement);
}
