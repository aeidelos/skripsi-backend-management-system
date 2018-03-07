package rizki.practicum.learning.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rizki.practicum.learning.dto.ResponseObject;
import rizki.practicum.learning.entity.Task;
import rizki.practicum.learning.service.task.TaskService;

import java.util.*;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value="/task/practican/{idpractican}/{status}", produces = {"application/json"})
    @ApiOperation(notes = "", value = "Mengambil data tugas praktikan tertentu")
    public @ResponseBody List<Task> getAllTaskPractican(
            @PathVariable("idpractican") String idPractican,
            @PathVariable("status") String status
    ) {
        List<Task> tasks = taskService.getTaskByPractican(idPractican, status);
        WebResponse.checkNullObject(tasks);
        return tasks;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value= "/task/{mode}/{id}/{time}",  produces = {"application/json"})
    @ApiOperation(notes = "", value = "Mengambil data tugas dengan filter masukan yang diinginkan")
    public @ResponseBody List<Task> getTask(
            @PathVariable("mode") String mode,
            @PathVariable("id") String id,
            @PathVariable("time") String time
    ) {
        List<Task> tasks = taskService.getTask(mode, id, time);
        WebResponse.checkNullObject(tasks);
        return tasks;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/task", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(notes = "", value = "Melakukan penambahan tugas baru")
    public @ResponseBody ResponseObject addTask(
            @ApiParam(name = "Task", value = "Tugas dalam format json", type = "Task")
            @RequestBody Task task
    ) {
        Task result = taskService.addTask(task);
        String category = new Date().before(result.getDueDate()) ? "current" : "past";
        Map<String, Object> map = new HashMap<>();
        map.put("task", result);
        WebResponse.checkNullObject(result);
        map.put("category", category);
        return ResponseObject
                .builder()
                .code(HttpStatus.CREATED.value())
                .message("Tugas berhasil ditambahkan")
                .status(HttpStatus.CREATED.getReasonPhrase())
                .object(map)
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/task/{idtask}")
    @ApiOperation(notes = "", value = "Menghapus Tugas Tertentu")
    public @ResponseBody
    ResponseObject deleteTask(
            @ApiParam(name = "idtask", value = "String (id_task) dalam format UUID", type = "String")
            @PathVariable("idtask") String idTask
    ) {
        taskService.deleteTask(idTask);
        return ResponseObject
                .builder()
                .code(HttpStatus.OK.value())
                .message("Tugas berhasil dihapus")
                .status(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/task/{idtask}", produces = {"application/json"}, consumes = {"application/json"})
    @ApiOperation(notes = "Object yang dikirim berupa json", value = "Mengubah tugas tertentu")
    public @ResponseBody
    ResponseObject<Object> updateTask(
            @ApiParam(name = "task", value = "Object tugas", type = "Task") @RequestBody Task task
    ) {
        Task result = taskService.updateTask(task);
        String category = new Date().before(result.getDueDate()) ? "current" : "past";
        Map<String, Object> map = new HashMap<>();
        WebResponse.checkNullObject(result);
        map.put("task", result);
        map.put("category", category);
        return ResponseObject
                .builder()
                .code(HttpStatus.OK.value())
                .message("Tugas berhasil diubah")
                .status(HttpStatus.OK.getReasonPhrase())
                .object(map)
                .build();
    }
}
