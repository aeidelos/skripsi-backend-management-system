package rizki.practicum.learning.configuration;

public interface Routes {

    interface AuthenticationRoutes {

    }

    interface UserRoutes {
        final String PREFIX = "/user";
        final String USER_REGISTER = PREFIX+"/add";
        final String USER_UPDATE = PREFIX+"/update/{id}";
        final String USER_REMOVE = PREFIX+"/delete";
    }

}
