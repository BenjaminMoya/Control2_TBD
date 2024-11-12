package Tdb.Control2Application.persistence.repository;

import Tdb.Control2Application.persistence.entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

@Repository
public class UserRepositoryImplementation implements UserRepository{

    @Autowired
    private Sql2o sql2o;

    @Override
    public UserEntity addUser(UserEntity user){
        String sql = "INSERT INTO users (user_name, user_password, user_email, is_auth) " +
                "VALUES (:userName, :userPassword, :userEmail, :isAuth)";

        try (org.sql2o.Connection con = sql2o.open()) {
            Long generatedId = (Long) con.createQuery(sql, true)
                    .addParameter("userName", user.getUserName())
                    .addParameter("userPassword", user.getUserPassword())
                    .addParameter("userEmail", user.getUserEmail())
                    .addParameter("isAuth", user.isAuth())
                    .executeUpdate()
                    .getKey();

            user.setUserId(generatedId);
            return user;
        }
    }
    @Override
    public UserEntity getById(Long userId) {
        try(org.sql2o.Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM view_user WHERE userId=:user_id")
                    .addParameter("user_id",userId)
                    .executeAndFetchFirst(UserEntity.class);
        }
    }

    @Override
    public UserEntity getByEmail(String userEmail) {
        try(org.sql2o.Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM view_user WHERE userEmail=:user_email")
                    .addParameter("user_email",userEmail)
                    .executeAndFetchFirst(UserEntity.class);
        }
    }

}
