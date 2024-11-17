package Tdb.Control2Application.persistence.repository;

import Tdb.Control2Application.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

@Repository
public class UserRepositoryImplementation implements UserRepository{

    @Autowired
    private Sql2o sql2o;

    @Override
    public UserEntity addUser(UserEntity user) {
        String sql = "INSERT INTO users (username, userpassword, useremail, isauth)" +
                "VALUES (:username, :userpassword, :useremail, :isauth)";

        try (org.sql2o.Connection con = sql2o.open()) {
            Long generatedId = (Long) con.createQuery(sql, true)
                    .addParameter("username", user.getUsername())
                    .addParameter("userpassword", user.getUserpassword())
                    .addParameter("useremail", user.getUseremail())
                    .addParameter("isauth", user.isIsauth())
                    .executeUpdate()
                    .getKey();

            user.setUserid(generatedId);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            throw e; // Manejar la excepción si es necesario
        }
    }

    @Override
    public UserEntity getById(Long userId) {
        try(org.sql2o.Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM users WHERE userid=:userid")
                    .addParameter("userid",userId)
                    .executeAndFetchFirst(UserEntity.class);
        }
    }

    @Override
    public UserEntity getByEmail(String userEmail) {
        try(org.sql2o.Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM users WHERE useremail=:useremail")
                    .addParameter("useremail",userEmail)
                    .executeAndFetchFirst(UserEntity.class);
        }
    }

}
