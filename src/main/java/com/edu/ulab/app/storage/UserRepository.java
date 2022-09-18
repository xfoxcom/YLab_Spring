package com.edu.ulab.app.storage;

import com.edu.ulab.app.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository {
    void save(User user);
    Optional<User> getUserById(long id);
    void deleteUserById(long id);
    boolean userNotExistById(long id);
}
