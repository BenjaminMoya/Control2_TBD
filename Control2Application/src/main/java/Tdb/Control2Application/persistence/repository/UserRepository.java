package Tdb.Control2Application.persistence.repository;

import Tdb.Control2Application.persistence.entity.UserEntity;

public interface UserRepository {
    UserEntity getById(Long userId);
}
