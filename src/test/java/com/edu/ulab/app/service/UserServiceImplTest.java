package com.edu.ulab.app.service;


import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.Person;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.repository.UserJpaRepository;
import com.edu.ulab.app.service.impl.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Тестирование функционала {@link com.edu.ulab.app.service.impl.UserServiceImpl}.
 */
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DisplayName("Testing user functionality.")
public class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserJpaRepository userRepository;

    @Mock
    UserMapper userMapper;

    @Test
    @DisplayName("Создание пользователя. Должно пройти успешно.")
    void savePerson_Test() {
        //given

        UserDto userDto = new UserDto();
        userDto.setAge(30);
        userDto.setFullName("test name");
        userDto.setTitle("test title");
        userDto.setPhone("6006565");

        Person person  = new Person();
        person.setFullName("test name");
        person.setAge(30);
        person.setTitle("test title");
        person.setPhone("6006565");

        Person savedPerson  = new Person();
        savedPerson.setId(100L);
        savedPerson.setFullName("test name");
        savedPerson.setAge(30);
        savedPerson.setTitle("test title");
        savedPerson.setPhone("6006565");

        UserDto result = new UserDto();
        result.setId(100L);
        result.setAge(30);
        result.setFullName("test name");
        result.setTitle("test title");
        result.setPhone("6006565");

        //when
        when(userMapper.userDtoToUser(userDto)).thenReturn(person);
        when(userRepository.save(person)).thenReturn(savedPerson);
        when(userMapper.userToUserDto(savedPerson)).thenReturn(result);

        //then
        UserDto userDtoResult = userService.createUser(userDto);
        assertEquals(100L, userDtoResult.getId());
    }

    @Test
    @DisplayName("Обновление пользователя. Должно пройти успешно.")
    void updatePerson_Test() {

        //Given
        UserDto userDto = new UserDto();
        userDto.setId(100L);
        userDto.setAge(30);
        userDto.setFullName("test name");
        userDto.setTitle("changed");
        userDto.setPhone("6006565");

        Person person  = new Person();
        person.setId(100L);
        person.setFullName("test name");
        person.setAge(30);
        person.setTitle("changed");
        person.setPhone("6006565");

        Person personChanged  = new Person();
        personChanged.setId(100L);
        personChanged.setFullName("test name");
        personChanged.setAge(30);
        personChanged.setTitle("changed");
        personChanged.setPhone("6006565");

        Person personChangedSaved  = new Person();
        personChangedSaved.setId(100L);
        personChangedSaved.setFullName("test name");
        personChangedSaved.setAge(30);
        personChangedSaved.setTitle("changed");
        personChangedSaved.setPhone("6006565");

        UserDto personChangedDto = new UserDto();
        personChangedDto.setId(100L);
        personChangedDto.setFullName("test name");
        personChangedDto.setTitle("changed");
        personChangedDto.setAge(30);
        personChangedDto.setPhone("6006565");

        //When
        when(userRepository.existsById(userDto.getId())).thenReturn(true);
        when(userMapper.userDtoToUser(userDto)).thenReturn(person);
        when(userRepository.save(personChanged)).thenReturn(personChangedSaved);
        when(userMapper.userToUserDto(personChangedSaved)).thenReturn(personChangedDto);

        //Then
        UserDto userDtoResult = userService.updateUser(userDto);
        assertEquals("changed", userDtoResult.getTitle());

    }

    @Test
    @DisplayName("Получение пользователя. Должно пройти успешно.")
    void getPerson_Test() {

        //Given
        UserDto userDto = new UserDto();
        userDto.setId(100L);
        userDto.setAge(30);
        userDto.setFullName("test name");
        userDto.setTitle("test title");
        userDto.setPhone("6006565");

        Person person  = new Person();
        person.setId(100L);
        person.setFullName("test name");
        person.setAge(30);
        person.setTitle("test title");
        person.setPhone("6006565");

        //When
        when(userRepository.getPersonById(anyLong())).thenReturn(Optional.of(person));
        when(userMapper.userToUserDto(person)).thenReturn(userDto);

        //Then
        UserDto userDtoResult = userService.getUserById(100L);
        assertEquals(100L, userDtoResult.getId());

    }

    @Test
    @DisplayName("Удаление пользователя. Должно пройти успешно.")
    void deletePerson_Test() {

        //Given
        Person person  = new Person();
        person.setId(100L);
        person.setFullName("test name");
        person.setAge(30);
        person.setTitle("test title");
        person.setPhone("6006565");

        //When
        when(userRepository.existsById(person.getId())).thenReturn(true);

        //Then
        userService.deleteUserById(person.getId());

        verify(userRepository, times(1)).deleteById(person.getId());

    }

    @Test
    @DisplayName("Fail test. Выбрасывает NotFoundException, когда нет юзера с id.")
    void getUserTest_NotFountException() {

        //When
        when(userRepository.existsById(anyLong())).thenReturn(false);

        //Then
        assertThrows(NotFoundException.class, () -> userService.getUserById(100L));

    }

    @Test
    @DisplayName("Fail test на удаление. Выводится сообщение, когда нет юзера с id.")
    void willThrowWithMessageWhenDeleteNotExistId () {
        //Given
        Person person  = new Person();
        person.setId(100L);
        person.setFullName("test name");
        person.setAge(30);
        person.setTitle("test title");
        person.setPhone("6006565");

        assertThatThrownBy(() -> userService.deleteUserById(person.getId())).hasMessageContaining("No user with id: " + person.getId());

    }
}