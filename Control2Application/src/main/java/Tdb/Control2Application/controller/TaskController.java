package Tdb.Control2Application.controller;

import Tdb.Control2Application.persistence.entity.TaskEntity;
import Tdb.Control2Application.persistence.entity.UserEntity;
import Tdb.Control2Application.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/addtask")
    public ResponseEntity<TaskEntity> addTask(@RequestBody TaskEntity task){
        try{
            return ResponseEntity.ok(taskService.addTask(task));
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/update/title/{id}")
    public void modifyTaskTitle(@PathVariable long id,
                                @RequestParam("title") String taskTitle){
        taskService.modifyTaskTitle(id,taskTitle);
    }

    @PostMapping("/update/desc/{id}")
    public void modifyTaskDesc(@PathVariable long id,
                               @RequestParam("desc") String taskDesc){
        taskService.modifyTaskDesc(id,taskDesc);
    }

    @PostMapping("/update/end/{id}")
    public void modifyTaskEndDate(@PathVariable long id,
                                  @RequestParam("date") LocalDate taskEndDate){
        taskService.modifyTaskEndDate(id,taskEndDate);
    }

    @PostMapping("/delete/{id}")
    public boolean deleteTask(@PathVariable long id){
        try{
            return taskService.deleteTask(id);
        }catch (Exception e){
            return false;
        }
    }

    @PostMapping("/complete/{id}")
    public void handleIsCompleted(@PathVariable long id){
        taskService.handleIsCompleted(id);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<TaskEntity> getTask(@PathVariable Long id){
        TaskEntity task = taskService.getTaskById(id);

        if(task != null){
            return ResponseEntity.ok(task);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/get/user/{id}")
    public ResponseEntity<List<TaskEntity>> getTaskByUserId(@PathVariable long id){
        List<TaskEntity> tasks = taskService.getTaskByUserId(id);

        if(!tasks.isEmpty()){
            return ResponseEntity.ok(tasks);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/get/completed/{id}")
    public ResponseEntity<List<TaskEntity>> getCompletedTasks(@PathVariable long id){
        List<TaskEntity> tasks = taskService.getCompletedTask(id);

        if(!tasks.isEmpty()){
            return ResponseEntity.ok(tasks);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/get/uncomplete/{id}")
    public ResponseEntity<List<TaskEntity>> getUncompletedTasks(@PathVariable long id){
        List<TaskEntity> tasks = taskService.getUncompletedTasks(id);

        if(!tasks.isEmpty()){
            return ResponseEntity.ok(tasks);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/get/title/{id}")
    public ResponseEntity<List<TaskEntity>> getTasksByTitleKeyword(@PathVariable long id,
                                                                   @RequestParam("title") String keyword){
        List<TaskEntity> tasks = taskService.getTasksByTitleKeyword(id,keyword);

        if(!tasks.isEmpty()){
            return ResponseEntity.ok(tasks);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/get/desc/{id}")
    public ResponseEntity<List<TaskEntity>> getTasksByDescriptionKeyword(@PathVariable long id,
                                                                         @RequestParam("desc") String keyword){
        List<TaskEntity> tasks = taskService.getTasksByDescriptionKeyword(id,keyword);

        if(!tasks.isEmpty()){
            return ResponseEntity.ok(tasks);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
