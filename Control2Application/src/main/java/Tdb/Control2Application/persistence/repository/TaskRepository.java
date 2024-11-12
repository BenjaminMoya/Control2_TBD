package Tdb.Control2Application.persistence.repository;

import Tdb.Control2Application.persistence.entity.TaskEntity;

public interface TaskRepository {

    TaskEntity addTask(TaskEntity task);
    TaskEntity getById(Long taskId);
    TaskEntity getByUserId(Long taskUserId);
    void modifyTaskTitle(TaskEntity task, String taskTitle);
    void modifyTaskDesc(TaskEntity task, String taskDesc);
    void modifyTaskExpireDate(TaskEntity task, String taskEndDate);
}
