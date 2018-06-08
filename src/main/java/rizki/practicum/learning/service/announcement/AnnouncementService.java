package rizki.practicum.learning.service.announcement;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import rizki.practicum.learning.entity.Announcement;

import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface AnnouncementService {
    /**
     *
     * @param announcement
     * @return
     */
    Announcement save(Announcement announcement);

    /**
     *
     * @param idClassroom
     * @param idPracticum
     * @param idUser
     * @return
     */
    Object get(String idClassroom, String idPracticum, String idUser);

    /**
     *
     * @param announcement
     */
    void delete(Announcement announcement);
}
