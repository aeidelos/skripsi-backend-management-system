package rizki.practicum.learning.entity;

import javax.persistence.Id;

public class UserSession {

    @Id
    private String id;

    private String token;

    private String role;

    private String userId;
}
