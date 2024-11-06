package Tdb.Control2Application.repositories;

import Tdb.Control2Application.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

@Repository
public class UserRepositoryImplementation implements UserRepository{

    @Autowired
    private Sql2o sql2o;
    @Override
    public UserModel getById(Long userId) {
        try(org.sql2o.Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM view_user WHERE userId=:user_id")
                    .addParameter("user_id",userId)
                    .executeAndFetchFirst(UserModel.class);
        }
    }
}
