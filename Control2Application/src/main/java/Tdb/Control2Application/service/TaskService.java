package Tdb.Control2Application.service;

import Tdb.Control2Application.persistence.entity.TaskEntity;
import Tdb.Control2Application.persistence.entity.UserEntity;
import Tdb.Control2Application.persistence.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public TaskEntity addTask(TaskEntity task){
        return taskRepository.addTask(task);
    }

    public void modifyTaskTitle(long taskId, String taskTitle){
        taskRepository.modifyTaskTitle(taskId,taskTitle);
    }

    public void modifyTaskDesc(long taskId, String taskDesc){
        taskRepository.modifyTaskDesc(taskId,taskDesc);
    }

    public void modifyTaskEndDate(long taskId, LocalDate taskEndDate){
        taskRepository.modifyTaskEndDate(taskId,taskEndDate);
    }

    public boolean deleteTask(Long taskId){
        try{
            taskRepository.deleteTask(taskId);
            return true;
        } catch (Exception e){
            return false;
        }

    }

    public void handleIsCompleted(long taskId){
        taskRepository.handleIsCompleted(taskId);
    }

    public TaskEntity getTaskById(Long id){ return taskRepository.getById(id); }

    public List<TaskEntity> getTaskByUserId(long userId){ return taskRepository.getByUserId(userId); }

    public List<TaskEntity> getCompletedTask(long userId){
        return taskRepository.getCompletedTasks(userId);
    }

    public List<TaskEntity> getUncompletedTasks(long userId){
        return taskRepository.getUncompletedTasks(userId);
    }

    public List<TaskEntity> getTasksByTitleKeyword(long userId, String keyword){
        return taskRepository.getTasksByTitleKeyword(userId,keyword);
    }

    public List<TaskEntity> getTasksByDescriptionKeyword(long userId, String keyword){
        return taskRepository.getTasksByDescriptionKeyword(userId,keyword);
    }

}
