//package rizki.practicum.learning.controller;
///*
//    Created by : Rizki Maulana Akbar, On 01 - 2018 ;
//*/
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//import rizki.practicum.learning.util.response.ResponseBuilder;
//
//import java.util.Map;
//
//@RestController
//public class AnnouncementController {
//
//    private HttpStatus httpStatus = null;
//    private String location = "";
//    private int statusResponse = 0;
//    private String message = "";
//    private Object body = null;
//
//    private void init(){
//        httpStatus = HttpStatus.OK;
//        location = "";
//        statusResponse = 0;
//        message = "";
//        body = null;
//    }
//
//    private ResponseEntity<Map<String,Object>> response(){
//        return new ResponseBuilder()
//                .setMessage(message)
//                .setLocation(location)
//                .setStatusCode(httpStatus)
//                .setStatusResponse(statusResponse)
//                .setBody(body)
//                .build();
//    }
//
//    @PostMapping
//    public ResponseEntity<Map<String, Object>> addAnnouncement(){
//        this.init();
//        return this.response();
//    }
//
//    @GetMapping
//    public ResponseEntity<Map<String, Object>> getAnnouncement(){
//        this.init();
//        return this.response();
//    }
//
//    @DeleteMapping
//    public ResponseEntity<Map<String, Object>> removeAnnouncement(){
//        this.init();
//        return this.response();
//    }
//
//
//
//
//
//}
