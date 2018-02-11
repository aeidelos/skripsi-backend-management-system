//package rizki.practicum.learning.service.announcement;
//
//import org.hibernate.validator.constraints.NotBlank;
//import org.springframework.data.domain.Pageable;
//import org.springframework.validation.annotation.Validated;
//import rizki.practicum.learning.entity.Announcement;
//
//import javax.validation.constraints.NotNull;
//import java.util.List;
//
//@Validated
//public interface AnnouncementService {
//    Announcement addAnnouncement(@NotBlank String title,@NotBlank String description,@NotBlank String idUser);
//    void deleteAnnouncement(@NotNull @NotBlank String idAnnouncement);
//    List<Announcement> getAnnouncement(Pageable pageable);
//}
