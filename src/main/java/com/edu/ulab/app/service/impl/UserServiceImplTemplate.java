package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.Person;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import com.edu.ulab.app.mapper.UserMapper;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImplTemplate implements UserService {

    private final JdbcTemplate jdbcTemplate;

    private final UserMapper userMapper;

    public UserServiceImplTemplate(JdbcTemplate jdbcTemplate, UserMapper userMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto createUser(UserDto userDto) {

        final String INSERT_SQL = "INSERT INTO PERSON(FULL_NAME, TITLE, AGE) VALUES (?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{"id"});
                    ps.setString(1, userDto.getFullName());
                    ps.setString(2, userDto.getTitle());
                    ps.setLong(3, userDto.getAge());
                    return ps;
                }, keyHolder);

        userDto.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return userDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto) {

        long userId = userDto.getId();

        final String UPDATE_SQL = "UPDATE PERSON SET FULL_NAME = ?, TITLE = ?, AGE = ? WHERE ID = ?;";

        final String INSERT_BOOKS_IDS = "INSERT INTO person_books_ids(person_id, books_ids) values (?, ?)";

        jdbcTemplate.update(
                con -> {
                    PreparedStatement ps = con.prepareStatement(UPDATE_SQL);
                    ps.setString(1, userDto.getFullName());
                    ps.setString(2, userDto.getTitle());
                    ps.setInt(3, userDto.getAge());
                    ps.setLong(4, userId);
                    return ps;
                }
        );

        List<Long> booksIds = userDto.getBooksIds();

        booksIds.stream().filter(Objects::nonNull).forEach(bookId -> jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(INSERT_BOOKS_IDS);
            ps.setLong(1, userId);
            ps.setLong(2, bookId);
            return ps;
        }));

        return userDto;
    }

    @Override
    public UserDto getUserById(Long id) {

        final String GET_PERSON = "SELECT * FROM PERSON WHERE ID = ?;";

        final String GET_BOOKS_ID = "SELECT BOOKS_IDS FROM person_books_ids WHERE PERSON_ID = ?";

        if (personNotExists(id)) throw new NotFoundException("No user with id: " + id);

        Person person = jdbcTemplate.queryForObject(GET_PERSON, new DataClassRowMapper<>(Person.class), id);
        log.info("Person: " + person);

            List<Long> booksIds = jdbcTemplate.queryForList(GET_BOOKS_ID, id)
                 .stream()
                 .filter(Objects::nonNull)
                 .map(e -> (Long)e.get("BOOKS_IDS"))
                 .collect(Collectors.toList());

         log.info("Books: " + booksIds);

        assert person != null;
        person.setBooksIds(booksIds);

         return userMapper.userToUserDto(person);
    }

    @Override
    public void deleteUserById(Long id) {

        final String DELETE_USER = "DELETE FROM PERSON WHERE ID = ?";

        final String DELETE_USER_RELATION = "DELETE FROM person_books_ids WHERE PERSON_ID = ?";

        jdbcTemplate.update(DELETE_USER_RELATION, id);

        jdbcTemplate.update(DELETE_USER, id);
        log.info("User with id " + id + " deleted.");
    }

    public boolean personNotExists(Long id) {

        final String COUNT_PERSON = "SELECT COUNT(*) FROM PERSON WHERE ID = ?";

        Long count = jdbcTemplate.queryForObject(COUNT_PERSON, Long.class, id);

        return count != null && count == 0;
    }
}
