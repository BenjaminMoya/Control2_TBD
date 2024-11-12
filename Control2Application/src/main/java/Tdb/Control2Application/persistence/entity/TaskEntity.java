package Tdb.Control2Application.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskEntity {

    private Long taskId;
    private Long taskUserId;
    private String taskTitle;
    private String taskDesc;
    private Date taskEndDate;
    private boolean isCompleted;
}
