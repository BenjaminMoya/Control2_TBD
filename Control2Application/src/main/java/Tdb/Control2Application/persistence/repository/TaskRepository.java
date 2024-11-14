package Tdb.Control2Application.persistence.repository;

import Tdb.Control2Application.persistence.entity.TaskEntity;

import java.util.Date;
import java.util.List;

public interface TaskRepository {

    TaskEntity addTask(TaskEntity task);
    void modifyTaskTitle(Long taskId, String taskTitle);
    void modifyTaskDesc(Long taskId, String taskDesc);
    void modifyTaskEndDate(Long taskId, Date taskEndDate);
    boolean deleteTask(Long taskId);
    void handleIsCompleted(Long taskId);
    TaskEntity getById(Long taskId);
    List<TaskEntity> getByUserId(Long taskUserId);
    List<TaskEntity> getCompletedTasks(Long userId);
    List<TaskEntity> getUncompletedTasks(Long userId);
    List<TaskEntity> getTasksByTitleKeyword(Long userId, String keyword);
    List<TaskEntity> getTasksByDescriptionKeyword(Long userId, String keyword);
}
