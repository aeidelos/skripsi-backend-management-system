package rizki.practicum.learning.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter @Getter
public class Practicum {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @OneToOne
    private User coordinatorAssistance;

    @ElementCollection(targetClass=Classroom.class)
    private List<Classroom> classrooms;

    @OneToOne
    private Course course;

    private boolean active = true;
}
