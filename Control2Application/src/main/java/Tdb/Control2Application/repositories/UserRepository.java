package Tdb.Control2Application.repositories;

import Tdb.Control2Application.models.UserModel;

public interface UserRepository {
    UserModel getById(Long userId);
}
