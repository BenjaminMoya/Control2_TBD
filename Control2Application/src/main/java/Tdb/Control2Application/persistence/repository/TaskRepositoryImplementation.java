package Tdb.Control2Application.persistence.repository;

import Tdb.Control2Application.persistence.entity.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Repository
public class TaskRepositoryImplementation implements TaskRepository{

    @Autowired
    private Sql2o sql2o;

    @Override
    public TaskEntity addTask(TaskEntity task) {
        String sql = "INSERT INTO tasks (taskuserid, tasktitle, taskdesc, taskend_date, iscompleted) " +
                "VALUES (:taskUserId, :taskTitle, :taskDesc, :taskEndDate, :isCompleted)";

        try (org.sql2o.Connection con = sql2o.open()) { // Usamos sql2o.open()
            Long generatedId = (Long) con.createQuery(sql, true)
                    .addParameter("taskUserId", task.getTaskuserid()) // Campo correcto
                    .addParameter("taskTitle", task.getTasktitle())
                    .addParameter("taskDesc", task.getTaskdesc())
                    .addParameter("taskEndDate", task.getTaskend_date())
                    .addParameter("isCompleted", task.isIscompleted())
                    .executeUpdate()
                    .getKey();

            task.setTaskid(generatedId); // Establecer el ID generado
            return task;
        } catch (Exception e) {
            // Manejamos cualquier error que ocurra
            e.printStackTrace();
            throw new RuntimeException("Error al agregar la tarea: " + e.getMessage(), e);
        }
    }



    @Override
    public void modifyTaskTitle(long taskId, String taskTitle) {
        try (org.sql2o.Connection con = sql2o.open()) {
            con.createQuery("UPDATE tasks SET tasktitle = :tasktitle WHERE taskid = :taskid")
                    .addParameter("tasktitle", taskTitle)
                    .addParameter("taskid", taskId)
                    .executeUpdate();
        }
    }

    @Override
    public void modifyTaskDesc(long taskId, String taskDesc) {
        try (org.sql2o.Connection con = sql2o.open()) {
            con.createQuery("UPDATE tasks SET taskdesc = :taskdesc WHERE taskid = :taskid")
                    .addParameter("taskdesc", taskDesc)
                    .addParameter("taskid", taskId)
                    .executeUpdate();
        }
    }

    @Override
    public void modifyTaskEndDate(long taskId, Date taskEndDate) {
        try (org.sql2o.Connection con = sql2o.open()) {
            con.createQuery("UPDATE tasks SET taskend_date = :taskend_date WHERE taskid = :taskid")
                    .addParameter("taskend_date", taskEndDate)
                    .addParameter("taskid", taskId)
                    .executeUpdate();
        }
    }

    @Override
    public boolean deleteTask(long taskId) {
        String sql = "DELETE FROM tasks WHERE taskid = :taskid";

        try (org.sql2o.Connection con = sql2o.open()) {
            int result = con.createQuery(sql)
                    .addParameter("taskid", taskId)
                    .executeUpdate()
                    .getResult();

            return result > 0;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void handleIsCompleted(long taskId) {
        try (org.sql2o.Connection con = sql2o.open()) {
            con.createQuery("UPDATE tasks SET iscompleted = NOT iscompleted WHERE taskid = :taskid")
                    .addParameter("taskid", taskId)
                    .executeUpdate();
        }
    }

    @Override
    public TaskEntity getById(long taskId) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM tasks WHERE taskid = :taskid")
                    .addParameter("taskid", taskId)
                    .executeAndFetchFirst(TaskEntity.class);
        }
    }

    @Override
    public List<TaskEntity> getByUserId(long taskUserId) {
        String sql = "SELECT taskid, taskuserid, tasktitle, taskdesc, taskend_date, iscompleted " +
                "FROM tasks WHERE taskuserid = :taskuserid";

        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("taskuserid", taskUserId)
                    .addColumnMapping("taskid", "taskid") // Mapeo explícito
                    .addColumnMapping("taskuserid", "taskuserid")
                    .addColumnMapping("tasktitle", "tasktitle")
                    .addColumnMapping("taskdesc", "taskdesc")
                    .addColumnMapping("taskend_date", "taskend_date")
                    .addColumnMapping("iscompleted", "iscompleted")
                    .executeAndFetch(TaskEntity.class);
        } catch (Exception e) {
            // Manejo de errores mejorado para depurar el problema
            System.err.println("Error al obtener tareas por userID: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al obtener tareas por usuario", e);
        }
    }



    @Override
    public List<TaskEntity> getCompletedTasks(long userId) {
        String sql = "SELECT * FROM tasks WHERE taskuserid = :taskuserid AND iscompleted = true";

        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("taskuserid", userId)
                    .executeAndFetch(TaskEntity.class);
        }
    }

    @Override
    public List<TaskEntity> getUncompletedTasks(long userId) {
        String sql = "SELECT * FROM tasks WHERE taskuserid = :taskuserid AND iscompleted = false";

        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("taskuserid", userId)
                    .executeAndFetch(TaskEntity.class);
        }
    }

    @Override
    public List<TaskEntity> getTasksByTitleKeyword(long userId, String keyword) {
        String sql = "SELECT * FROM tasks WHERE taskuserid = :taskuserid AND tasktitle LIKE :keyword";

        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("taskuserid", userId)
                    .addParameter("keyword", "%" + keyword + "%")
                    .executeAndFetch(TaskEntity.class);
        }
    }

    @Override
    public List<TaskEntity> getTasksByDescriptionKeyword(long userId, String keyword) {
        String sql = "SELECT * FROM tasks WHERE taskuserid = :taskuserid AND taskdesc LIKE :keyword";

        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("taskuserid", userId)
                    .addParameter("keyword", "%" + keyword + "%")
                    .executeAndFetch(TaskEntity.class);
        }
    }





}
