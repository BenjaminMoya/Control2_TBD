package Tdb.Control2Application.persistence.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    private Long userId;
    private String userName;
    private String userPassword;
    private String userEmail;
    private boolean isAuth;

}
