package Tdb.Control2Application.controller;

import Tdb.Control2Application.persistence.entity.UserEntity;
import Tdb.Control2Application.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/get/{id}")
    public ResponseEntity<UserEntity> getUser(@PathVariable Long id){
        UserEntity user = userService.getUserById(id);

        if(user != null){
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /*
    @PostMapping("/register")
    public  ResponseEntity<UserEntity> registerUser(@RequestBody Map<String,String> credentials){
        try{
            String userName = credentials.get("userName");
            String userPassword = credentials.get("userPassword");
            String userEmail = credentials.get("userEmail");
            System.out.println(userName);
            System.out.println(userPassword);
            System.out.println(userEmail);

            UserEntity user = new UserEntity();
            user.setUserName(userName);
            user.setUserPassword(userPassword);
            user.setUserEmail(userEmail);

            userService.addUser(user);

            return ResponseEntity.ok(userService.addUser(user));
            //return "ok";
        } catch (Exception e){
            return ResponseEntity.notFound().build();
            //return "error";
        }
    }
    */

    @PostMapping("/register")
    public ResponseEntity<UserEntity> registerUser(@RequestBody UserEntity user) {
        try {
            return ResponseEntity.ok(userService.addUser(user)); // Guardar usuario
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public boolean login(@RequestParam("email") String email,
                         @RequestParam("password") String password){
        return userService.login(email,password);
    }
}
