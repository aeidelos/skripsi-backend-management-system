package rizki.practicum.learning.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Document {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @ManyToOne(targetEntity = User.class)
    private User practican;

    @ManyToOne
    private Assignment assignment;

    @Column
    private String filename;

    @Column
    private double grade = 0;

    @Column
    private boolean markAsPlagiarized = false;

}
