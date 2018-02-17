package rizki.practicum.learning.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
@Data
public class Role {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Setter @Getter
    private String id;

    @Size(max = 25)
    @Column(nullable = false, unique = true)
    @Setter @Getter
    private String description;

    @Size(max = 25)
    @Column(nullable = false, unique = true)
    @Setter @Getter
    private String initial;

}