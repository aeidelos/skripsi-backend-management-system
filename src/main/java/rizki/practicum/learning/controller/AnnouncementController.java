package rizki.practicum.learning.controller;
/*
    Created by : Rizki Maulana Akbar, On 01 - 2018 ;
*/
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rizki.practicum.learning.dto.ResponseObject;
import rizki.practicum.learning.entity.Announcement;
import rizki.practicum.learning.service.announcement.AnnouncementService;

@RestController
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Insert atau Update pengumuman")
    @PutMapping(value = "/announcement" , produces = {"application/json"})
    public @ResponseBody Announcement addAnnouncement(
            @RequestBody Announcement announcement
    ){
        return WebResponse.verify(announcementService.save(announcement));
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Hapus pengumuman")
    @DeleteMapping(value = "/announcement", produces = {"application/json"})
    public @ResponseBody
    ResponseObject delete (@RequestBody Announcement announcement) {
        WebResponse.verify(announcement);
        announcementService.delete(announcement);
        return ResponseObject
                .builder()
                .code(HttpStatus.OK.value())
                .message("Pengumuman berhasil dihapus")
                .status(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @ApiOperation("Mendapatkan pengumuman")
    @PostMapping("/announcement")
    @ResponseStatus(HttpStatus.OK)
    public Object getAnnouncement(
            @RequestParam (value = "idClassroom", required = false) String idClassroom,
            @RequestParam (value = "idPracticum", required = false) String idPracticum,
            @RequestParam (value = "idUser", required = false) String idUser
    ){
        return WebResponse.verify(announcementService.get(idClassroom, idPracticum, idUser));
    }
}
