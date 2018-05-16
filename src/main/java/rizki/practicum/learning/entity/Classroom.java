package rizki.practicum.learning.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.Null;
import java.util.List;

@Entity
@Data
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

    @Column
    @Nullable
    private String date;

    @ManyToOne(targetEntity = Practicum.class)
    private Practicum practicum;

    @ManyToMany
    private List<User> assistance;

    @ManyToMany
    private List<User> practican;
}
