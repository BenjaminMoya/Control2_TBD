package Tdb.Control2Application.persistence.repository;

import Tdb.Control2Application.persistence.entity.UserEntity;

public interface UserRepository {

    UserEntity addUser(UserEntity user);
    UserEntity getById(Long userId);
    UserEntity getByEmail(String userEmail);
}
