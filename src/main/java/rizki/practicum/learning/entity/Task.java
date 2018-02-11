package rizki.practicum.learning.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

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

    @DateTimeFormat @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date createdDate;

    @DateTimeFormat @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date dueDate;

    @Column
    private boolean allowLate = false;

    @ManyToOne
    private User createdBy;

    @ManyToOne @Nullable
    private Classroom classroom;

    @ManyToOne @Nullable
    private Practicum practicum;

}
