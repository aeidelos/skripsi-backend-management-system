package rizki.practicum.learning.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@Setter @Getter
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

    @OneToMany(targetEntity = Document.class)
    private Map<Document, Float> plagiarism;

}
