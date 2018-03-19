package rizki.practicum.learning.entity;
/*
    Created by : Rizki Maulana Akbar, On 01 - 2018 ;
*/

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Announcement {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @Column
    private String title;
    @Column
    private String description;

    @ManyToOne @Nullable
    private Classroom classroom;

    @ManyToOne @Nullable
    private Practicum practicum;

    @ManyToOne
    private User createdBy;

    @DateTimeFormat
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date createdDate;

    @DateTimeFormat
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date activeUntil;

}
