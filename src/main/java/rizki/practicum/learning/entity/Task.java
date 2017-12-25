package rizki.practicum.learning.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Setter @Getter
public class Task {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column
    @NotNull
    private String title;

    @Column
    private String description;

    @DateTimeFormat
    private Date createdDate;

    @DateTimeFormat
    private Date dueDate;

    @Column
    private boolean allowLate = false;

    @ManyToOne
    private User createdBy;

    @ManyToOne
    private Classroom classroom;

    @ManyToOne
    private Practicum practicum;

    @OneToMany
    private List<Assignment> assignments;

}
