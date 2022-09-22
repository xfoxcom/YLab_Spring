package com.edu.ulab.app.storage;

import com.edu.ulab.app.entity.Book;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository {
    void save(Book book);
    Optional<Book> getBookById(long id);
    boolean bookNotExistById(long id);
    void deleteBookById(long id);
}
