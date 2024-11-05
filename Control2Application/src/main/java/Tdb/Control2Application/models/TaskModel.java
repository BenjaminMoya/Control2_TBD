package Tdb.Control2Application.models;

import lombok.Data;

import java.util.Date;

@Data
public class TaskModel {

    private Long taskId;
    private Long taskUserId;
    private String taskTitle;
    private String taskDesc;
    private Date taskEndDate;
    private boolean isCompleted;
}
