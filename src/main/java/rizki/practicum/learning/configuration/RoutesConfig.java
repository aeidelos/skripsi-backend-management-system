package rizki.practicum.learning.configuration;

public interface RoutesConfig {

    interface AuthenticationRoutes {
        final String PREFIX = "/auth";
        final String LOGIN = PREFIX+"/login";
        final String LOGOUT = PREFIX+"/logout";
    }

    interface UserRoutes {
        final String PREFIX = "/user";
        final String USER_REGISTER = PREFIX+"/add";
        final String USER_UPDATE = PREFIX+"/update/{id}";
        final String USER_REMOVE = PREFIX+"/delete";
    }


}
