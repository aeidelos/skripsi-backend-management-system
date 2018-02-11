//package rizki.practicum.learning.entity;
///*
//    Created by : Rizki Maulana Akbar, On 01 - 2018 ;
//*/
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import lombok.Getter;
//import lombok.Setter;
//import org.hibernate.annotations.GenericGenerator;
//import org.springframework.format.annotation.DateTimeFormat;
//
//import javax.persistence.*;
//import java.util.Date;
//
//@Entity
//@Setter @Getter
//public class Announcement {
//
//    public Announcement(){ this.createdDate = new Date(); }
//    public Announcement(String title, String description, User user){
//        this.title = title;
//        this.description = description;
//        this.createdBy = user;
//        this.createdDate = new Date();
//    }
//
//
//    @Id
//    @GeneratedValue(generator = "uuid")
//    @GenericGenerator(name = "uuid", strategy = "uuid2")
//    private String id;
//    @Column
//    private String title;
//    @Column
//    private String description;
//    private User createdBy;
//    @DateTimeFormat
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
//    private Date createdDate;
//
//}
