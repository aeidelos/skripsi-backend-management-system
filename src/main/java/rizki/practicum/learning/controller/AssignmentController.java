package rizki.practicum.learning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import rizki.practicum.learning.entity.Assignment;
import rizki.practicum.learning.repository.AssignmentRepository;
import rizki.practicum.learning.service.assignment.AssignmentService;
import rizki.practicum.learning.service.task.TaskService;
import rizki.practicum.learning.util.response.ResponseBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AssignmentController {
    private HttpStatus httpStatus = null;
    private String location = "";
    private int statusResponse = 0;
    private String message = "";
    private Object body = null;

    private void init(){
        httpStatus = HttpStatus.OK;
        location = "";
        statusResponse = 0;
        message = "";
        body = null;
    }

    @Autowired
    private TaskService taskService;

    @Autowired
    private AssignmentService assignmentService;

    private ResponseEntity<Map<String,Object>> response(){
        return new ResponseBuilder()
                .setMessage(message)
                .setLocation(location)
                .setStatusCode(httpStatus)
                .setStatusResponse(statusResponse)
                .setBody(body)
                .build();
    }

    @GetMapping("/assignment/{idtask}")
    public ResponseEntity<Map<String,Object>> getAssignment(
        @PathVariable("idtask") String idTask
    ){
        this.init();
        try{
            List<Assignment> assignments = assignmentService.getAssignmentByTask(idTask);
            statusResponse = 1;
            Map<String, Object> map = new HashMap<>();
            map.put("assigments",assignments);
            body = map;
        }catch(IllegalArgumentException e){
            e.printStackTrace();
            message = "Assignment not found";
        }
        return this.response();
    }

    @PostMapping("/assignment")
    public ResponseEntity<Map<String, Object>> addAssignment(

    ){
        this.init();

        return this.response();
    }

}
