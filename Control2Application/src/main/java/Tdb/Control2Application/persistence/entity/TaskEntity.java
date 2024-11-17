package Tdb.Control2Application.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskEntity {

    private long taskid;
    private long taskuserid;
    private String tasktitle;
    private String taskdesc;
    private Date taskend_date;
    private boolean iscompleted;
}
