package Tdb.Control2Application.service;

import Tdb.Control2Application.persistence.entity.TaskEntity;
import Tdb.Control2Application.persistence.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public TaskEntity addTask(TaskEntity task){
        return taskRepository.addTask(task);
    }

    public void modifyTaskTitle(Long taskId, String taskTitle){
        taskRepository.modifyTaskTitle(taskId,taskTitle);
    }

    public void modifyTaskDesc(Long taskId, String taskDesc){
        taskRepository.modifyTaskDesc(taskId,taskDesc);
    }

    public void modifyTaskEndDate(Long taskId, Date taskEndDate){
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

    public void handleIsCompleted(Long taskId){
        taskRepository.handleIsCompleted(taskId);
    }

    public TaskEntity getTaskById(Long id){ return taskRepository.getById(id); }

    public List<TaskEntity> getTaskByUserId(Long userId){ return taskRepository.getByUserId(userId); }

    public List<TaskEntity> getCompletedTask(Long userId){
        return taskRepository.getCompletedTasks(userId);
    }

    public List<TaskEntity> getUncompletedTasks(Long userId){
        return taskRepository.getUncompletedTasks(userId);
    }

    public List<TaskEntity> getTasksByTitleKeyword(Long userId, String keyword){
        return taskRepository.getTasksByTitleKeyword(userId,keyword);
    }

    public List<TaskEntity> getTasksByDescriptionKeyword(Long userId, String keyword){
        return taskRepository.getTasksByDescriptionKeyword(userId,keyword);
    }

}
