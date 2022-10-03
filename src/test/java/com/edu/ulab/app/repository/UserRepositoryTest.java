package com.edu.ulab.app.repository;

import com.edu.ulab.app.config.SystemJpaTest;
import com.edu.ulab.app.entity.Person;
import com.vladmihalcea.sql.SQLStatementCountValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityNotFoundException;

import static com.vladmihalcea.sql.SQLStatementCountValidator.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

/**
 * Тесты репозитория {@link UserJpaRepository}.
 */
@SystemJpaTest
public class UserRepositoryTest {

    @Autowired
    UserJpaRepository userRepository;

    @BeforeEach
    void setUp() {
        SQLStatementCountValidator.reset();
    }

    @DisplayName("Сохранить юзера. Число select должно равняться 1")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql"
    })
    void insertPerson_thenAssertDmlCount() {
        //Given
        Person person = new Person();
        person.setAge(30);
        person.setTitle("reader");
        person.setFullName("Test Test");
        person.setPhone("+74996006161");

        //When
        Person result = userRepository.save(person);


        //Then
        assertThat(result.getAge()).isEqualTo(30);
        assertSelectCount(1);
        assertInsertCount(0);
        assertUpdateCount(0);
        assertDeleteCount(0);
    }

    @DisplayName("Обновить юзера. Число select должно равняться 1")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql"
    })
    void updatePerson_thenAssertDmlCount() {

        //When
        Person person = userRepository.getReferenceById(1001L);

        person.setAge(55);

        Person result = userRepository.save(person);

        //Then
        assertThat(result.getAge()).isEqualTo(55);
        assertSelectCount(1);
        assertInsertCount(0);
        assertUpdateCount(0);
        assertDeleteCount(0);
    }

    @DisplayName("Получить юзера. Число select должно равняться 0")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql"
    })
    void getPerson_thenAssertDmlCount() {

        //When
        Person result = userRepository.getReferenceById(1001L);

        //Then
        assertThat(result.getId()).isEqualTo(1001L);
        assertSelectCount(0);
        assertInsertCount(0);
        assertUpdateCount(0);
        assertDeleteCount(0);
    }

    @DisplayName("Удалить юзера. Число select должно равняться 2, delete 2")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql",
            "classpath:sql/4_insert_person_book_ids_data.sql"
    })
    void deletePerson_thenAssertDmlCount() {

        //When
        userRepository.deleteById(1001L);

        boolean result = userRepository.existsById(1001L);

        //Then
        assertThat(result).isFalse();
        assertSelectCount(2);
        assertInsertCount(0);
        assertUpdateCount(0);
        assertDeleteCount(2);
    }

    @DisplayName("Фэйл тетс на получение юзера с неверным id. Выбрасывется EntityNotFoundException.")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql"
    })
    void getPersonById_FailTest() {

        //Given
        Person person = userRepository.getReferenceById(666L);

        //Then
        assertThrows(EntityNotFoundException.class, () -> person.setAge(59));

    }

}