package Tdb.Control2Application.persistence.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    private Long userid;
    private String username;
    private String userpassword;
    private String useremail;
    //private boolean isAuth;

}
