package com.edu.ulab.app.storage;

import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.entity.User;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.*;

@Data
@Component
public class Storage implements UserRepository, BookRepository {

    private Map<Long, User> usersTable = new HashMap<>();
    private Map<Long, Book> booksTable = new HashMap<>();

    public void save(User user) {
       usersTable.put(user.getId(), user);
    }

    public void save(Book book) {
        booksTable.put(book.getId(), book);
    }

    public Optional<User> getUserById(long id) {
       return Optional.ofNullable(usersTable.get(id));
    }

    public Optional<Book> getBookById(long id) {
        return Optional.ofNullable(booksTable.get(id));
    }

    public void deleteUserById(long id) {
        usersTable.remove(id);
    }

    public void deleteBookById(long id) {
        booksTable.remove(id);
    }

    public boolean userNotExistById(long id) {
        return !usersTable.containsKey(id);
    }

    public boolean bookNotExistById(long id) {
        return !booksTable.containsKey(id);
    }

    //todo создать хранилище в котором будут содержаться данные
    // сделать абстракции через которые можно будет производить операции с хранилищем
    // продумать логику поиска и сохранения
    // продумать возможные ошибки
    // учесть, что при сохранеии юзера или книги, должен генерироваться идентификатор
    // продумать что у узера может быть много книг и нужно создать эту связь
    // так же учесть, что методы хранилища принимают другой тип данных - учесть это в абстракции
}
