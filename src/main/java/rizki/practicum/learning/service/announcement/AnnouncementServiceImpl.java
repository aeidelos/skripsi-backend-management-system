//package rizki.practicum.learning.service.announcement;
///*
//    Created by : Rizki Maulana Akbar, On 01 - 2018 ;
//*/
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//import rizki.practicum.learning.entity.Announcement;
//import rizki.practicum.learning.repository.AnnouncementRepository;
//import rizki.practicum.learning.repository.UserRepository;
//
//import java.util.Date;
//import java.util.List;
//
//@Service
//public class AnnouncementServiceImpl implements AnnouncementService{
//
//    @Autowired
//    private AnnouncementRepository announcementRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public Announcement addAnnouncement(String title, String description, String idUser) {
//        return announcementRepository.save(
//                new Announcement(title, description, userRepository.findOne(idUser))
//        );
//    }
//
//    @Override
//    public void deleteAnnouncement(String idAnnouncement) {
//        announcementRepository.delete(idAnnouncement);
//    }
//
//    @Override
//    public List<Announcement> getAnnouncement(Pageable pageable) {
//        return (List<Announcement>) announcementRepository.findAllOrderByCreatedDateAsc(pageable);
//    }
//}
