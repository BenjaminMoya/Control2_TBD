package Tdb.Control2Application.persistence.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TaskEntity {

    private Long taskId;
    private Long taskUserId;
    private String taskTitle;
    private String taskDesc;
    private Date taskEndDate;
    private boolean isCompleted;
}
