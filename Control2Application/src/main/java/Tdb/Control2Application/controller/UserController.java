package Tdb.Control2Application.controller;

import Tdb.Control2Application.persistence.entity.UserEntity;
import Tdb.Control2Application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/user")
public class UserController  {

    @Autowired
    private UserService userService;

    @GetMapping("/getemail/{email}")
    public ResponseEntity<UserEntity> getUserByEmail(@PathVariable("email") String email) {
        UserEntity user = userService.getUserByEmail(email);

        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

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
