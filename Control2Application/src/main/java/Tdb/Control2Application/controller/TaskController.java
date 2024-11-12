package Tdb.Control2Application.controller;

import Tdb.Control2Application.persistence.entity.TaskEntity;
import Tdb.Control2Application.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/get/{id}")
    public ResponseEntity<TaskEntity> getTaskById(@PathVariable Long id){
        TaskEntity task = taskService.getTaskById(id);

        if(task != null){
            return ResponseEntity.ok(task);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/get/user/{id}")
    public ResponseEntity<TaskEntity> getTaskByUserId(@PathVariable Long id){
        TaskEntity task = taskService.getTaskByUserId(id);

        if(task != null){
            return ResponseEntity.ok(task);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<TaskEntity> addTask(@RequestBody TaskEntity task){
        try{
            return ResponseEntity.ok(taskService.addTask(task));
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

}
