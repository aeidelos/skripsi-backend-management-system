package rizki.practicum.learning.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
public class Role {

    @Setter @Getter
    @Id
    @GeneratedValue(generator ="uuid")
    @GenericGenerator(name="uuid", strategy = "uuid2")
    private String id;

    @Setter @Getter
    @Size(max = 25)
    @Column(nullable = false, unique = true)
    private String description;
}
