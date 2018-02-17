package rizki.practicum.learning.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
public class User {

    @GeneratedValue(generator ="uuid")
    @GenericGenerator(name="uuid", strategy = "uuid2")
    @Id
    private String id;

    @Column(nullable=false)
    @Size(max = 40)
    private String name;

    @Column(nullable = false, unique = true)
    @Size(max=20)
    @NotNull
    @NotBlank
    private String identity;

    @Column(nullable = false)
    private String password;

    @Size(max=40)
    @Column(nullable = false, unique = true)
    private String email;

    private String photo;

    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @ManyToMany(targetEntity=Role.class,fetch = FetchType.EAGER)
    private List<Role> role;

    private boolean active = true;

}
