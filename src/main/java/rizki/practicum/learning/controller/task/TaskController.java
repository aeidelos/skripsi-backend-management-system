package rizki.practicum.learning.controller.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rizki.practicum.learning.configuration.RoutesConfig;
import rizki.practicum.learning.entity.Assignment;
import rizki.practicum.learning.entity.Document;
import rizki.practicum.learning.entity.Task;
import rizki.practicum.learning.exception.ExceptionMessage;
import rizki.practicum.learning.service.assignment.AssignmentService;
import rizki.practicum.learning.service.authorization.AuthorizationService;
import rizki.practicum.learning.service.classroom.ClassroomService;
import rizki.practicum.learning.service.plagiarism.PlagiarismService;
import rizki.practicum.learning.service.practicum.PracticumService;
import rizki.practicum.learning.service.task.TaskService;
import rizki.practicum.learning.service.user.UserService;
import rizki.practicum.learning.util.response.ResponseWrapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private PracticumService practicumService;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private ResponseWrapper responseWrapper;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private PlagiarismService plagiarismService;

    @PostMapping(RoutesConfig.PracticumRoutes.TaskRoutes.TASK_ADD)
    public ResponseEntity<Map<String,Object>> addTask
            (@RequestParam("title") String title,
             @RequestParam("creator") String idCreator,
             @RequestParam("allowLate") boolean allowLate,
             @RequestParam("description") String description,
             @RequestParam("createdDate") Date createdDate,
             @RequestParam("dueDate") Date dueDate,
             @RequestParam("classroom") String classroom,
             @RequestParam("practicum") String practicum
            )
    {
        try{
            Task newTask = new Task();
            newTask.setTitle(title);
            newTask.setCreatedBy(userService.getUser(idCreator));
            newTask.setAllowLate(allowLate);
            newTask.setDescription(description);
            newTask.setCreatedDate(createdDate);
            newTask.setDueDate(dueDate);
            if(classroom!=null && practicum==null){
                newTask.setClassroom(classroomService.getClassroom(classroom));
            } else if (practicum != null) {
                newTask.setPracticum(practicumService.getDetailPracticum(practicum));
            }else{
                return responseWrapper.restResponseWrapper(HttpStatus.CREATED,null,
                        RoutesConfig.PracticumRoutes.TaskRoutes.TASK_ADD,
                        1, "");
            }
            Task result = taskService.addTask(newTask);
            if(result!=null){
                return responseWrapper.restResponseWrapper(HttpStatus.CREATED,null,
                        RoutesConfig.PracticumRoutes.CourseRoutes.COURSE_ADD,
                        1, ExceptionMessage.Practicum.Course.COURSE_CREATED);
            }else{
                return responseWrapper.restResponseWrapper(HttpStatus.OK,null,
                        RoutesConfig.PracticumRoutes.CourseRoutes.COURSE_ADD,
                        0, ExceptionMessage.Practicum.Course.COURSE_CREATED_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return responseWrapper.restResponseWrapper(HttpStatus.NOT_ACCEPTABLE,e.getMessage(),
                    RoutesConfig.PracticumRoutes.CourseRoutes.COURSE_ADD,
                    0,null);
        }
    }

    @PostMapping(RoutesConfig.PracticumRoutes.TaskRoutes.TASK_UPDATE)
    public ResponseEntity<Map<String,Object>> updateTask
            (@PathVariable("id_task") String idTask,
             @RequestParam("title") String title,
             @RequestParam("creator") String idCreator,
             @RequestParam("allowLate") boolean allowLate,
             @RequestParam("description") String description,
             @RequestParam("createdDate") Date createdDate,
             @RequestParam("dueDate") Date dueDate
            )
    {
        try{
            Task newTask = taskService.getTask(idTask);
            newTask.setTitle(title);
            newTask.setCreatedBy(userService.getUser(idCreator));
            newTask.setAllowLate(allowLate);
            newTask.setDescription(description);
            newTask.setCreatedDate(createdDate);
            newTask.setDueDate(dueDate);
            Task result = taskService.updateTask(newTask);
            if(result!=null){
                return responseWrapper.restResponseWrapper(HttpStatus.OK,null,
                        RoutesConfig.PracticumRoutes.CourseRoutes.COURSE_ADD,
                        1, ExceptionMessage.Practicum.Course.COURSE_CREATED);
            }else{
                return responseWrapper.restResponseWrapper(HttpStatus.OK,null,
                        RoutesConfig.PracticumRoutes.CourseRoutes.COURSE_ADD,
                        0, ExceptionMessage.Practicum.Course.COURSE_CREATED_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return responseWrapper.restResponseWrapper(HttpStatus.NOT_ACCEPTABLE,e.getMessage(),
                    RoutesConfig.PracticumRoutes.CourseRoutes.COURSE_ADD,
                    0,null);
        }
    }

    @PostMapping(RoutesConfig.PracticumRoutes.TaskRoutes.TASK_PRACTICUM)
    public ResponseEntity<Map<String,Object>> getTaskByPracticum
            (@PathVariable("id_practicum") String idPracticum)
    {
        try{
            List<Task> tasks = taskService.getTaskByPracticum(idPracticum);
            return responseWrapper.restResponseWrapper(HttpStatus.OK,tasks,
                    RoutesConfig.PracticumRoutes.TaskRoutes.TASK_PRACTICUM,
                    1, "");
        }catch(Exception e){
            e.printStackTrace();
            return responseWrapper.restResponseWrapper(HttpStatus.OK,null,
                    RoutesConfig.PracticumRoutes.TaskRoutes.TASK_PRACTICUM,
                    0, "");
        }
    }

    @PostMapping(RoutesConfig.PracticumRoutes.TaskRoutes.TASK_CLASSROOM)
    public ResponseEntity<Map<String,Object>> getTaskByClassroom
            (@PathVariable("id_classroom") String idClassroom)
    {
        try{
            List<Task> tasks = taskService.getTaskByClassroom(idClassroom);
            return responseWrapper.restResponseWrapper(HttpStatus.OK,tasks,
                    RoutesConfig.PracticumRoutes.TaskRoutes.TASK_CLASSROOM,
                    1, "");
        }catch(Exception e){
            e.printStackTrace();
            return responseWrapper.restResponseWrapper(HttpStatus.OK,null,
                    RoutesConfig.PracticumRoutes.TaskRoutes.TASK_CLASSROOM,
                    0, "");
        }
    }

    @PostMapping(RoutesConfig.PracticumRoutes.TaskRoutes.TASK_CREATOR)
    public ResponseEntity<Map<String,Object>> getTaskByCreator
            (@PathVariable("id_creator") String idCreator)
    {
        try{
            List<Task> tasks = taskService.getTaskByCreator(idCreator);
            return responseWrapper.restResponseWrapper(HttpStatus.OK,tasks,
                    RoutesConfig.PracticumRoutes.TaskRoutes.TASK_CREATOR,
                    1, "");
        }catch(Exception e){
            e.printStackTrace();
            return responseWrapper.restResponseWrapper(HttpStatus.OK,null,
                    RoutesConfig.PracticumRoutes.TaskRoutes.TASK_CREATOR,
                    0, "");
        }
    }

    @GetMapping(RoutesConfig.PracticumRoutes.TaskRoutes.TASK_DETAIL)
    public ResponseEntity<Map<String,Object>> getTaskDetails
            (@PathVariable("id_task") String idTask)
    {
        try{
            Task task = taskService.getTask(idTask);
            return responseWrapper.restResponseWrapper(HttpStatus.OK,task,
                    RoutesConfig.PracticumRoutes.TaskRoutes.TASK_DETAIL,
                    1, "");
        }catch(Exception e){
            e.printStackTrace();
            return responseWrapper.restResponseWrapper(HttpStatus.OK,null,
                    RoutesConfig.PracticumRoutes.TaskRoutes.TASK_DETAIL,
                    0, "");
        }
    }

    @GetMapping(RoutesConfig.PracticumRoutes.TaskRoutes.PREFIX)
    public ResponseEntity<Map<String,Object>> getAllTask()
    {
        try{
            List<Task> task = taskService.getTask();
            return responseWrapper.restResponseWrapper(HttpStatus.OK,task,
                    RoutesConfig.PracticumRoutes.TaskRoutes.TASK_DETAIL,
                    1, "");
        }catch(Exception e){
            e.printStackTrace();
            return responseWrapper.restResponseWrapper(HttpStatus.OK,null,
                    RoutesConfig.PracticumRoutes.TaskRoutes.TASK_DETAIL,
                    0, "");
        }
    }


    @PostMapping(RoutesConfig.PracticumRoutes.TaskRoutes.TASK_ADD_ASSIGNMENT)
    public ResponseEntity<Map<String,Object>> addAssignment
            (@PathVariable("id_task") String idTask,
             @RequestParam("description") String description,
             @RequestParam("file_allowed") String fileAllowed
            )
    {
        try{
            Assignment assignment = new Assignment();
            assignment.setDescription(description);
            assignment.setFileAllowed(fileAllowed);
            Assignment result = assignmentService.addAssignment(idTask,assignment);
            if(result!=null){
                return responseWrapper.restResponseWrapper(HttpStatus.CREATED,null,
                        RoutesConfig.PracticumRoutes.TaskRoutes.TASK_ADD_ASSIGNMENT,
                        1, "");
            }else{
                return responseWrapper.restResponseWrapper(HttpStatus.OK,null,
                        RoutesConfig.PracticumRoutes.TaskRoutes.TASK_ADD_ASSIGNMENT,
                        0, "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return responseWrapper.restResponseWrapper(HttpStatus.NOT_ACCEPTABLE,e.getMessage(),
                    RoutesConfig.PracticumRoutes.TaskRoutes.TASK_ADD_ASSIGNMENT,
                    0,null);
        }
    }



    @GetMapping(RoutesConfig.PracticumRoutes.TaskRoutes.TASK_LIST_ASSIGNMENT_BY_TASK)
    public ResponseEntity<Map<String,Object>> addAssignment
            (@PathVariable("id_task") String idTask
            )
    {
        try{
            List<Assignment> result = assignmentService.getAssignmentByTask(idTask);
            if(result!=null){
                return responseWrapper.restResponseWrapper(HttpStatus.OK,result,
                        RoutesConfig.PracticumRoutes.TaskRoutes.TASK_LIST_ASSIGNMENT_BY_TASK,
                        1, "");
            }else{
                return responseWrapper.restResponseWrapper(HttpStatus.OK,null,
                        RoutesConfig.PracticumRoutes.TaskRoutes.TASK_LIST_ASSIGNMENT_BY_TASK,
                        0, "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return responseWrapper.restResponseWrapper(HttpStatus.NOT_ACCEPTABLE,e.getMessage(),
                    RoutesConfig.PracticumRoutes.TaskRoutes.TASK_LIST_ASSIGNMENT_BY_TASK,
                    0,null);
        }
    }


    @GetMapping(RoutesConfig.PracticumRoutes.TaskRoutes.TASK_DELETE_ASSIGNMENT)
    public ResponseEntity<Map<String,Object>> deleteAssignment
            (@PathVariable("id_assignment") String idAssignment
            )
    {
        try{
            assignmentService.deleteAssignment(idAssignment);
            return responseWrapper.restResponseWrapper(HttpStatus.OK,"",
                    RoutesConfig.PracticumRoutes.TaskRoutes.TASK_ADD_ASSIGNMENT,
                    1,null);
        } catch (Exception e) {
            e.printStackTrace();
            return responseWrapper.restResponseWrapper(HttpStatus.NOT_ACCEPTABLE,e.getMessage(),
                    RoutesConfig.PracticumRoutes.TaskRoutes.TASK_ADD_ASSIGNMENT,
                    0,null);
        }
    }

    @PostMapping(RoutesConfig.PracticumRoutes.TaskRoutes.TASK_UPLOAD_ASSIGNMENT)
    public ResponseEntity<Map<String, Object>> uploadAssignment(
        @PathVariable("id_assignment") String idAssignment,
        @RequestParam("file_upload") MultipartFile file
    )
    {
        String idPractican = authorizationService.getUserDetails().getId();
        Document upload = new Document();
        try {
            upload = assignmentService.documentAssignment(idAssignment,file,idPractican);
        } catch (Exception e) {
            return responseWrapper.restResponseWrapper(HttpStatus.OK,"",
                    RoutesConfig.PracticumRoutes.TaskRoutes.TASK_UPLOAD_ASSIGNMENT,
                    0,e.getMessage());
        }
        if(upload.getId() == null){
            return responseWrapper.restResponseWrapper(HttpStatus.OK,"",
                    RoutesConfig.PracticumRoutes.TaskRoutes.TASK_UPLOAD_ASSIGNMENT,
                    0,"Gagal melakukan upload");
        }else{
            try {
                plagiarismService.checkPlagiarism(upload.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return responseWrapper.restResponseWrapper(HttpStatus.CREATED,"",
                    RoutesConfig.PracticumRoutes.TaskRoutes.TASK_UPLOAD_ASSIGNMENT,
                    1,"Berhasil melakukan upload");
        }
    }

    @PostMapping(RoutesConfig.PracticumRoutes.TaskRoutes.TASK_DELETE_UPLOADED_ASSIGNMENT)
    public ResponseEntity<Map<String,Object>> deleteUploadedAssignment(
            @PathVariable("id_document") String idDocument
    )
    {
        try {
            if(assignmentService.documentAssignmentDelete(idDocument)){
                return responseWrapper.restResponseWrapper(HttpStatus.OK,"",
                        RoutesConfig.PracticumRoutes.TaskRoutes.TASK_DELETE_UPLOADED_ASSIGNMENT,
                        1,"Berhasil menghapus dokumen");
            }else{
                return responseWrapper.restResponseWrapper(HttpStatus.OK,"",
                        RoutesConfig.PracticumRoutes.TaskRoutes.TASK_DELETE_UPLOADED_ASSIGNMENT,
                        0,"Gagal menghapus dokumen");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return responseWrapper.restResponseWrapper(HttpStatus.OK,"",
                    RoutesConfig.PracticumRoutes.TaskRoutes.TASK_DELETE_UPLOADED_ASSIGNMENT,
                    0,e.getMessage());
        }
    }


}
