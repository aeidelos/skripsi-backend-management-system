package rizki.practicum.learning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rizki.practicum.learning.entity.Task;
import rizki.practicum.learning.service.task.TaskService;
import rizki.practicum.learning.util.response.ResponseBuilder;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TaskController {
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

    private ResponseEntity<Map<String,Object>> response(){
        return new ResponseBuilder()
                .setMessage(message)
                .setLocation(location)
                .setStatusCode(httpStatus)
                .setStatusResponse(statusResponse)
                .setBody(body)
                .build();
    }

    @GetMapping("/task/practican/{idpractican}/{status}")
    public ResponseEntity<Map<String, Object>> getAllTaskPractican(
            @PathVariable("idpractican") String idPractican,
            @PathVariable("status") String status
    ){
        this.init();
        try{
            statusResponse = 1;
            List<Task> tasks = taskService.getTaskByPractican(idPractican, status);
            Map<String, Object> map = new HashMap<>();
            map.put("tasks",tasks);
            body = map;
        }catch(IllegalArgumentException e){
            message = "Id praktikan tidak boleh kosong :"+e.getMessage().toString();
        }
        return this.response();
    }

    @GetMapping("/task/{mode}/{id}/{time}")
    public ResponseEntity<Map<String,Object>> getTask(
        @PathVariable("mode") String mode,
        @PathVariable("id") String id,
        @PathVariable("time") String time
    ){
        this.init();
        try{
            statusResponse = 1;
            List <Task> tasks = taskService.getTask(mode, id, time);
            Map<String, Object> map = new HashMap<>();
            map.put("tasks",tasks);
            body = map;
        }catch(IllegalArgumentException e){
            e.printStackTrace();
            message = "Parameter tidak valid :"+e.getMessage().toString();
        }
        return this.response();
    }

    @PostMapping("/task")
    public ResponseEntity<Map<String, Object>> addTask(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("startdate") Date startDate,
            @RequestParam("duedate") Date dueDate,
            @RequestParam("allowlate") boolean allowLate,
            @RequestParam("iduser") String idUser,
            @RequestParam("idclassroom") String idClassroom,
            @RequestParam("idpracticum") String idPracticum
    )
    {
        this.init();
        try{
            httpStatus = HttpStatus.CREATED;
            statusResponse = 1;
            Task task = taskService.addTask(title, description, startDate, dueDate, allowLate, idUser, idClassroom, idPracticum);
            Map<String, Object> map = new HashMap<>();
            map.put("task",task);
            message = "Tugas berhasil ditambahkan";
            body = map;
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            message =  "Tugas gagal ditambahkan, cek input parameter :"+e.getMessage().toString();
        }
        return this.response();
    }

    @DeleteMapping("/task/{idtask}")
    public ResponseEntity<Map<String, Object>> deleteTask(
            @PathVariable("idtask") String idTask
    ){
        this.init();
        try{
            taskService.deleteTask(idTask);
            statusResponse = 1;
            message = "Task berhasil dihapus";
        }catch(IllegalArgumentException e){
            e.printStackTrace();
            message = "Pastikan id sudah ada :"+e.getMessage().toString();
        }

        return this.response();
    }

    @PutMapping("/task/{idtask}")
    public ResponseEntity<Map<String,Object>> updateTask(
            @PathVariable("idtask") String idTask,
            @RequestBody Task task
    ){
        this.init();
        try{
            statusResponse = 1;
            Task updatedTask = taskService.updateTask(task);
            Map<String, Object> map = new HashMap<>();
            map.put("task",task);
            message = "Tugas berhasil diubah";
        }catch(IllegalArgumentException e){
            e.printStackTrace();
            message =  "Tugas gagal diubah, cek input parameter :"+e.getMessage().toString();
        }
        return this.response();
    }


}
