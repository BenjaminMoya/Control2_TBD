package Tdb.Control2Application.persistence.repository;

import Tdb.Control2Application.persistence.entity.TaskEntity;

import java.util.List;

public interface TaskRepository {

    TaskEntity addTask(TaskEntity task);
    TaskEntity getById(Long taskId);
    TaskEntity getByUserId(Long taskUserId);
    void modifyTaskTitle(TaskEntity task, String taskTitle);
    void modifyTaskDesc(TaskEntity task, String taskDesc);
    void modifyTaskExpireDate(TaskEntity task, String taskEndDate);
    void handleIsCompleted(TaskEntity task);
    List<TaskEntity> getCompletedTask(Long userId);
    List<TaskEntity> getUncompletedTask(Long userId);
    boolean deleteTask(Long taskId);
}
