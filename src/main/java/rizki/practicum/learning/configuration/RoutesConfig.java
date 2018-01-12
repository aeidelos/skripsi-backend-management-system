package rizki.practicum.learning.configuration;

public interface RoutesConfig {
    String VALIDITY_TOKEN = "/check/user";
    interface UserRoutes {
        String PREFIX = "/user";
        String USER_REGISTER = PREFIX+"/add";
        String USER_UPDATE = PREFIX+"/update/{id}";
        String USER_UPDATE_NAME = PREFIX+"/updateName/{id}";
        String USER_UPDATE_PASSWORD = PREFIX+"/changePassword/{id}";
        String USER_UPDATE_PHOTO = PREFIX+"/updatePhoto/{id}";
        String USER_REMOVE = PREFIX+"/delete";
    }
    interface PracticumRoutes {
        String PREFIX ="/practicum";
        String PRACTICUM_ADD = PREFIX+"/add";
        String PRACTICUM_UPDATE_COORDINATOR_ASSISTANCE = PREFIX+"/coordinator/{id_practicum}";
        interface ClassroomRoutes {
            String PREFIX = PracticumRoutes.PREFIX+"/class";
            String CLASSROOM_DETAIL = PREFIX+"/{id_practicum}";
            String CLASSROOM_ADD = PREFIX+"/add/practicum/{id_practicum}";
            String CLASSROOM_UPDATE = PREFIX+"/add/practicum/{id_classroom}";
            String CLASSROOM_DELETE = PREFIX+"/delete/{id_classroom}";
            String ASSIGN_ASSISTANT = PREFIX+"/assign/assistant/{id_classroom}";
            String ASSIGN_PRACTICAN = PREFIX+"/assign/practicant/";
        }
        interface CourseRoutes {
            String PREFIX = PracticumRoutes.PREFIX+"/course";
            String COURSE_GET = PREFIX+"/details/{id_course}";
            String COURSE_ADD = PREFIX+"/add";
            String COURSE_UPDATE = PREFIX+"/update/{id_course}";
            String COURSE_DELETE = PREFIX+"/delete/{id_course}";
        }
        interface TaskRoutes {
            String PREFIX = PracticumRoutes.PREFIX+"/task/";
            String TASK_ADD = PREFIX+"/add";
            String TASK_DETAIL = PREFIX+"/detail/{id_task}";
            String TASK_PRACTICUM = PREFIX+"/practicum/{id_practicum}";
            String TASK_CREATOR = PREFIX+"/creator/{id_creator}";
            String TASK_CLASSROOM = PREFIX+"/classroom/{id_classroom}";
            String TASK_UPDATE = PREFIX+"/update/{id_task}";
            String TASK_LIST_ASSIGNMENT_BY_TASK = PREFIX+"/assignment/list/{id_task}";
            String TASK_ADD_ASSIGNMENT = PREFIX+"/assignment/add/task/{id_task}";
            String TASK_DELETE_ASSIGNMENT = PREFIX+"/assignment/delete/{id_assignment}";
            String TASK_UPLOAD_ASSIGNMENT = PREFIX+"/assignment/upload/{id_assignment}";
            String TASK_DELETE_UPLOADED_ASSIGNMENT = PREFIX+"/assignment/deleteUploaded/{id_document}";
        }
    }
}
