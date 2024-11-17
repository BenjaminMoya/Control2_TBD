package Tdb.Control2Application.persistence.repository;

import Tdb.Control2Application.persistence.entity.TaskEntity;

import java.util.Date;
import java.util.List;

public interface TaskRepository {

    TaskEntity addTask(TaskEntity task);
    void modifyTaskTitle(long taskId, String taskTitle);
    void modifyTaskDesc(long taskId, String taskDesc);
    void modifyTaskEndDate(long taskId, Date taskEndDate);
    boolean deleteTask(long taskId);
    void handleIsCompleted(long taskId);
    TaskEntity getById(long taskId);
    List<TaskEntity> getByUserId(long taskUserId);
    List<TaskEntity> getCompletedTasks(long userId);
    List<TaskEntity> getUncompletedTasks(long userId);
    List<TaskEntity> getTasksByTitleKeyword(long userId, String keyword);
    List<TaskEntity> getTasksByDescriptionKeyword(long userId, String keyword);
}
