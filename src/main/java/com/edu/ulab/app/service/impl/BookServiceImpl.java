package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.storage.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class BookServiceImpl implements BookService {

    private long ID = 1;

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public BookDto createBook(BookDto bookDto) {

        bookDto.setId(ID++);

        Book book = bookMapper.bookDtoToBook(bookDto);

        bookRepository.save(book);

        return bookDto;
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {

        if (bookRepository.bookNotExistById(bookDto.getId())) throw new NotFoundException("No Book with id:  " + bookDto.getId());

        Book book = bookMapper.bookDtoToBook(bookDto);
        log.info("Updated book: " + book);

        bookRepository.save(book);

        return bookDto;
    }

    @Override
    public BookDto getBookById(Long id) {

        Book book = bookRepository.getBookById(id)
                .orElseThrow(() -> new NotFoundException("No book with id: " + id));

        log.info("Got book: " + book);

        return bookMapper.bookToBookDto(book);
    }

    @Override
    public void deleteBookById(Long id) {

        if (bookRepository.bookNotExistById(id)) throw new NotFoundException("No Book with id:  " + id);

        bookRepository.deleteBookById(id);
        log.info("Book with id " + id + " deleted.");
    }
}
