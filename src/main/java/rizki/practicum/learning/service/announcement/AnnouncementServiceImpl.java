package rizki.practicum.learning.service.announcement;
/*
    Created by : Rizki Maulana Akbar, On 01 - 2018 ;
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rizki.practicum.learning.entity.Announcement;
import rizki.practicum.learning.repository.AnnouncementRepository;
import rizki.practicum.learning.repository.ClassroomRepository;
import rizki.practicum.learning.repository.PracticumRepository;
import rizki.practicum.learning.repository.UserRepository;

import java.util.Calendar;
import java.util.Date;

@Service
public class AnnouncementServiceImpl implements AnnouncementService{

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private PracticumRepository practicumRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Announcement save(Announcement announcement) {
        if (announcement.getId() == null && announcement.getId()=="") announcementRepository.delete(announcement.getId());
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, 7);
        announcement.setCreatedDate(new Date());
        announcement.setActiveUntil(cal.getTime());
        return announcementRepository.save(announcement);
    }

    @Override
    public Object get(String idClassroom, String idPracticum, String idUser) {
        if (idClassroom != null) {
            return announcementRepository.findByClassroom(classroomRepository.findOne(idClassroom));
        } else if (idPracticum != null) {
            return announcementRepository.findByPracticum(practicumRepository.findOne(idPracticum));
        } else {
            return announcementRepository.getAnnouncementForUser(userRepository.findOne(idUser));
        }
    }

    @Override
    public void delete(Announcement announcement) {
        announcementRepository.delete(announcement);
    }
}
