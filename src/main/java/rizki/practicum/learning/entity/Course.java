package rizki.practicum.learning.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Setter @Getter
@Entity
public class Course {

    public Course(){}
    public Course(String id, String courseCode, String courseName){
        this.id = id;
        this.courseCode = courseCode;
        this.courseName = courseName;
    }

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(unique = true)
    private String courseCode;

    @Column
    private String courseName;
}
