package rizki.practicum.learning.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class User {

    @Setter @Getter
    @GeneratedValue(generator ="uuid")
    @GenericGenerator(name="uuid", strategy = "uuid2")
    @Id
    private String id;

    @Setter @Getter
    @Column(nullable=false, unique = false)
    @Size(max = 40)
    private String name;

    @Setter @Getter
    @Column(nullable = false, unique = true)
    @Size(max=20)
    private String identity;

    @Setter @Getter
    @Column(nullable = false)
    private String password;


    @Setter @Getter
    @Size(max=40)
    @Column(nullable = false, unique = true)
    private String email;

    @Setter @Getter
    private String photo;

    @Setter @Getter
    @OneToOne
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Role role;

}
