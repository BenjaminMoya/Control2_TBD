package Tdb.Control2Application.service;

import Tdb.Control2Application.persistence.entity.UserEntity;
import Tdb.Control2Application.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.getByEmail(email);
        if (userEntity == null){
            throw new UsernameNotFoundException("User not found");
        }
        return User.builder()
                .username(userEntity.getUseremail())
                .password(userEntity.getUserpassword()) // Asegúrate de que la contraseña esté encriptada
                .build();
    }
}
