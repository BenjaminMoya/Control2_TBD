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
        try (Connection con = sql2o.beginTransaction()) { // Usa una transacción
            String sql = "INSERT INTO users (username, userpassword, useremail) VALUES (:username, :userpassword, :useremail)";

            con.createQuery(sql)
                    .addParameter("username", user.getUsername())
                    .addParameter("userpassword", user.getUserpassword())
                    .addParameter("useremail", user.getUseremail())
                    .executeUpdate();
            con.commit(); // Confirmar la transacción
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            throw e; // Manejar la excepción si es necesario
        }
    }
    /*
    @Override
    public UserEntity addUser(UserEntity user) {
        String sql = "INSERT INTO users (username, userpassword, useremail) VALUES (:username, :userpassword, :useremail)";
        try (org.sql2o.Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("username", user.getUsername())
                    .addParameter("userpassword", user.getUserpassword())
                    .addParameter("useremail", user.getUseremail())
                    .executeUpdate();
            return user;
        }
    }
    */

    /*
    @Override
    public UserEntity addUser(UserEntity user){
        String sql = "INSERT INTO users (username, userpassword, useremail) " +
                "VALUES (:username, :userpassword, :useremail) RETURNING userid";

        try (Connection con = sql2o.open()) {
            Long userId = con.createQuery(sql)
                    .addParameter("userName", user.getUserName())
                    .addParameter("userPassword", user.getUserPassword())
                    .addParameter("userEmail", user.getUserEmail())
                    .executeAndFetchFirst(Long.class);

            user.setUserId(userId); // Asignar el ID generado a la entidad
            return user;
        }
    }
    */

    @Override
    public UserEntity getById(Long userId) {
        try(org.sql2o.Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM user WHERE userId=:user_id")
                    .addParameter("user_id",userId)
                    .executeAndFetchFirst(UserEntity.class);
        }
    }

    @Override
    public UserEntity getByEmail(String userEmail) {
        try(org.sql2o.Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM user WHERE userEmail=:user_email")
                    .addParameter("user_email",userEmail)
                    .executeAndFetchFirst(UserEntity.class);
        }
    }

}
