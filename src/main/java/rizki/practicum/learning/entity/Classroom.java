package rizki.practicum.learning.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter @Getter
public class Classroom {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column
    private String name;

    @Column
    private String location;

    @Column
    private String enrollmentKey;

    @ManyToOne(targetEntity = Practicum.class)
    private Practicum practicum;

    @OneToMany
    private List<User> assistance;

    @OneToMany
    private List<User> practican;
}
