package com.edu.ulab.app.storage;

import com.edu.ulab.app.entity.Person;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {
    void save(Person person);
    Optional<Person> getUserById(long id);
    void deleteUserById(long id);
    boolean userNotExistById(long id);
}
