package Tdb.Control2Application.controller;

import Tdb.Control2Application.persistence.entity.UserEntity;
import Tdb.Control2Application.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/register")
    public ResponseEntity<UserEntity> registerUser(@RequestBody UserEntity user){
        try{
            return ResponseEntity.ok(userService.addUser(user));
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/login")
    public boolean login(@RequestParam("email") String email,
                         @RequestParam("password") String password){
        return userService.login(email,password);
    }
}
