package Tdb.Control2Application.persistence.repository;

import Tdb.Control2Application.persistence.entity.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

@Repository
public class TaskRepositoryImplementation implements TaskRepository{

    @Autowired
    private Sql2o sql2o;

    @Override
    public TaskEntity addTask(TaskEntity task){
        String sql = "INSERT INTO tasks (task_user_id, task_title, task_desc, task_end_date,is_completed) " +
                "VALUES (:taskUserId, :taskTitle, :taskDesc, :taskEndDate, :isCompleted)";

        try (org.sql2o.Connection con = sql2o.open()) {
            Long generatedId = (Long) con.createQuery(sql, true)
                    .addParameter("taskUserId", task.getTaskUserId())
                    .addParameter("taskTitle", task.getTaskTitle())
                    .addParameter("taskDesc", task.getTaskDesc())
                    .addParameter("taskEndDate",task.getTaskEndDate())
                    .addParameter("isCompleted", task.isCompleted())
                    .executeUpdate()
                    .getKey();

            task.setTaskId(generatedId);
            return task;
        }
    }
    @Override
    public TaskEntity getById(Long taskId) {
        try(org.sql2o.Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM view_task WHERE taskId=:task_id")
                    .addParameter("task_id",taskId)
                    .executeAndFetchFirst(TaskEntity.class);
        }
    }

    @Override
    public TaskEntity getByUserId(Long taskUserId) {
        try(org.sql2o.Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM view_task WHERE taskUserId=:task_user_id")
                    .addParameter("task_user_id",taskUserId)
                    .executeAndFetchFirst(TaskEntity.class);
        }
    }
}
