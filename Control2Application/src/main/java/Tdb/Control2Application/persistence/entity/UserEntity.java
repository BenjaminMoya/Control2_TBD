package Tdb.Control2Application.persistence.entity;
import lombok.Data;

@Data
public class UserEntity {

    private Long userId;
    private String userName;
    private String userPassword;
    private String userEmail;
    private boolean isAuth;

}
