package Tdb.Control2Application.models;
import lombok.Data;

@Data
public class UserModel {

    private Long userId;
    private String userName;
    private String userPassword;
    private String userEmail;
    private boolean isAuth;

}
