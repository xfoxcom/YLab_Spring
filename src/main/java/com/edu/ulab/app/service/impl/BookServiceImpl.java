package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.repository.BookJpaRepository;
import com.edu.ulab.app.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class BookServiceImpl implements BookService {

    private final BookJpaRepository bookRepository;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookJpaRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public BookDto createBook(BookDto bookDto) {

        Book book = bookMapper.bookDtoToBook(bookDto);

        Book savedBook = bookRepository.save(book);

        return bookMapper.bookToBookDto(savedBook);
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {

        if (!bookRepository.existsById(bookDto.getId())) throw new NotFoundException("No Book with id:  " + bookDto.getId());

        Book book = bookMapper.bookDtoToBook(bookDto);
        log.info("Updated book: " + book);

        bookRepository.save(book);

        return bookDto;
    }

    @Override
    public BookDto getBookById(Long id) {

        if (!bookRepository.existsById(id)) throw new NotFoundException("No Book with id:  " + id);

        Book book = bookRepository.getReferenceById(id);

        log.info("Got book: " + book);

        return bookMapper.bookToBookDto(book);
    }

    @Override
    public void deleteBookById(Long id) {

        if (!bookRepository.existsById(id)) throw new NotFoundException("No Book with id:  " + id);

        bookRepository.deleteById(id);
        log.info("Book with id " + id + " deleted.");
    }
}
