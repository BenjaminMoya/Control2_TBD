package Tdb.Control2Application.service;

import Tdb.Control2Application.persistence.entity.TaskEntity;
import Tdb.Control2Application.persistence.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public TaskEntity addTask(TaskEntity task){
        return taskRepository.addTask(task);
    }

    public TaskEntity getTaskById(Long id){
        return taskRepository.getById(id);
    }

    public TaskEntity getTaskByUserId(Long userId){
        return taskRepository.getByUserId(userId);
    }
}
