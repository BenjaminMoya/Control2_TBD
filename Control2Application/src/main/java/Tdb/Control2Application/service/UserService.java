package Tdb.Control2Application.service;

import Tdb.Control2Application.persistence.entity.UserEntity;
import Tdb.Control2Application.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity getUserById(Long id){
        return userRepository.getById(id);
    }

    public UserEntity getUserByEmail(String email){
        return userRepository.getByEmail(email);
    }

    public UserEntity addUser(UserEntity user){
        return userRepository.addUser(user);
    }

    public boolean login (String email, String password){
        UserEntity user = getUserByEmail(email);
        return user != null && Objects.equals(password, user.getUserpassword());
    }
}
