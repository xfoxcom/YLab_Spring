package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

@Slf4j
@Service
public class BookServiceImplTemplate implements BookService {

    private final JdbcTemplate jdbcTemplate;

    private final BookMapper bookMapper;

    public BookServiceImplTemplate(JdbcTemplate jdbcTemplate, BookMapper bookMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.bookMapper = bookMapper;
    }

    @Override
    public BookDto createBook(BookDto bookDto) {
        final String INSERT_SQL = "INSERT INTO BOOK(TITLE, AUTHOR, PAGE_COUNT, USER_ID) VALUES (?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps =
                                connection.prepareStatement(INSERT_SQL, new String[]{"id"});
                        ps.setString(1, bookDto.getTitle());
                        ps.setString(2, bookDto.getAuthor());
                        ps.setLong(3, bookDto.getPageCount());
                        ps.setLong(4, bookDto.getUser_id());
                        return ps;
                    }
                },
                keyHolder);

        bookDto.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return bookDto;
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        // реализовать недстающие методы

        final String UPDATE_SQL = "UPDATE BOOK SET TITLE = ?, AUTHOR = ?, PAGECOUNT = ? WHERE ID = ?;";

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(UPDATE_SQL);
            ps.setString(1, bookDto.getTitle());
            ps.setString(2, bookDto.getAuthor());
            ps.setLong(3, bookDto.getPageCount());
            ps.setLong(4, bookDto.getId());
            return ps;
        });

        return bookDto;
    }

    @Override
    public BookDto getBookById(Long id) {
        // реализовать недстающие методы

        final String GET_BOOK = "SELECT * FROM BOOK WHERE ID = ?";

        if (bookNotExists(id)) throw new NotFoundException("No book with id: " + id);

        Book book = jdbcTemplate.queryForObject(GET_BOOK, new DataClassRowMapper<>(Book.class), id);
        log.info("Book: " + book);

        return bookMapper.bookToBookDto(book);
    }

    @Override
    public void deleteBookById(Long id) {
        // реализовать недстающие методы

        final String DELETE_BOOK = "DELETE FROM BOOK WHERE ID = ?";

        if (bookNotExists(id)) throw new NotFoundException("No book with id: " + id);

        jdbcTemplate.update(DELETE_BOOK, id);
        log.info("Book with id " + id + " deleted.");
    }

    public boolean bookNotExists(Long id) {

        final String COUNT_BOOK = "SELECT COUNT(*) FROM BOOK WHERE ID = ?";

        Long count = jdbcTemplate.queryForObject(COUNT_BOOK, Long.class, id);

        return count != null && count == 0;
    }
}
