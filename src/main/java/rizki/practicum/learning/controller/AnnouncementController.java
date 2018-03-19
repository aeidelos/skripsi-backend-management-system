package rizki.practicum.learning.controller;
/*
    Created by : Rizki Maulana Akbar, On 01 - 2018 ;
*/

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rizki.practicum.learning.entity.Announcement;
import rizki.practicum.learning.service.announcement.AnnouncementService;
import rizki.practicum.learning.service.assignment.AssignmentService;
import rizki.practicum.learning.util.response.ResponseBuilder;

import java.util.Collection;
import java.util.Map;

@RestController
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Insert atau Update pengumuman")
    @PutMapping(value = "/announcement" , produces = {"application/json"}, consumes = {"application/json"})
    public @ResponseBody Announcement addAnnouncement(
            @RequestBody Announcement announcement
    ){
        return WebResponse.checkNullObject(announcementService.save(announcement));
    }

    @ApiOperation("Mendapatkan pengumuman")
    @PostMapping("/announcement")
    @ResponseStatus(HttpStatus.OK)
    public Object getAnnouncement(
            @RequestParam (value = "idClassroom", required = false) String idClassroom,
            @RequestParam (value = "idPracticum", required = false) String idPracticum,
            @RequestParam (value = "idUser", required = false) String idUser
    ){
        return announcementService.get(idClassroom, idPracticum, idUser);
    }






}
