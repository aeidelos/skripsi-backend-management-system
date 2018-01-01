package rizki.practicum.learning.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Setter @Getter
public class User {

    @GeneratedValue(generator ="uuid")
    @GenericGenerator(name="uuid", strategy = "uuid2")
    @Id
    private String id;

    @Column(nullable=false, unique = false)
    @Size(max = 40)
    private String name;

    @Column(nullable = false, unique = true)
    @Size(max=20)
    private String identity;

    @Column(nullable = false)
    private String password;

    @Size(max=40)
    @Column(nullable = false, unique = true)
    private String email;

    private String photo;

    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @OneToMany(targetEntity=Role.class,fetch = FetchType.EAGER)
    private List<Role> role;

    private boolean active = true;

}
