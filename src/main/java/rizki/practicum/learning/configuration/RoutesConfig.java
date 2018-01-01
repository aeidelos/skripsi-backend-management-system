package rizki.practicum.learning.configuration;

public interface RoutesConfig {
    String VALIDITY_TOKEN = "/checkToken";
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
    interface PracticumRoutes {
        final String PREFIX ="/practicum";
        final String PRACTICUM_ADD = PREFIX+"/add";
        final String PRACTICUM_UPDATE_COORDINATOR_ASSISTANCE = PREFIX+"/coordinator/{id_practicum}";
        interface ClassroomRoutes {
            final String PREFIX = PracticumRoutes.PREFIX+"/class";
            final String CLASSROOM_DETAIL = PREFIX+"/{id_practicum}";
            final String CLASSROOM_ADD = PREFIX+"/add/practicum/{id_practicum}";
            final String CLASSROOM_UPDATE = PREFIX+"/add/practicum/{id_classroom}";
            final String CLASSROOM_DELETE = PREFIX+"/delete/{id_classroom}";
            final String ASSIGN_ASSISTANT = PREFIX+"/assign/assistant/{id_classroom}";
            final String ASSIGN_PRACTICAN = PREFIX+"/assign/practicant/";
        }
        interface CourseRoutes {
            final String PREFIX = PracticumRoutes.PREFIX+"/course";
            final String COURSE_GET = PREFIX+"/details/{id_course}";
            final String COURSE_ADD = PREFIX+"/add";
            final String COURSE_UPDATE = PREFIX+"/update/{id_course}";
            final String COURSE_DELETE = PREFIX+"/delete/{id_course}";
        }
        interface TaskRoutes {
            final String PREFIX = PracticumRoutes.PREFIX+"/task/";
            final String TASK_ADD = PREFIX+"/add";
            final String TASK_DETAIL = PREFIX+"/detail/{id_task}";
            final String TASK_PRACTICUM = PREFIX+"/practicum/{id_practicum}";
            final String TASK_CREATOR = PREFIX+"/creator/{id_creator}";
            final String TASK_CLASSROOM = PREFIX+"/classroom/{id_classroom}";
            final String TASK_UPDATE = PREFIX+"/update/{id_task}";
            final String TASK_ADD_ASSIGNMENT = PREFIX+"/assignment/add/task/{id_task}";
            final String TASK_DELETE_ASSIGNMENT = PREFIX+"/assignment/delete/{id_assignment}";
            final String TASK_UPLOAD_ASSIGNMENT = PREFIX+"/assignment/upload/{id_assignment}";
            final String TASK_DELETE_UPLOADED_ASSIGNMENT = PREFIX+"/assignment/deleteUploaded/{id_document}";
        }
    }

}
