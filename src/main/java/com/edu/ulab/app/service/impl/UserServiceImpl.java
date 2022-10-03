package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.Person;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.repository.UserJpaRepository;
import com.edu.ulab.app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserJpaRepository userRepository;

    public UserServiceImpl(UserMapper userMapper, UserJpaRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(UserDto userDto) {

        Person person = userMapper.userDtoToUser(userDto);

        Person savedUser =  userRepository.save(person);

        return userMapper.userToUserDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {

       if (!userRepository.existsById(userDto.getId())) throw new NotFoundException("No user with id: " + userDto.getId());
       log.info("Updated DTO: " + userDto);

        Person person = userMapper.userDtoToUser(userDto);
        log.info("Saving user to DB: " + person);

        userRepository.save(person);

        return userDto;
    }

    @Override
    public UserDto getUserById(Long id)  {

        Person person = userRepository.getPersonById(id).orElseThrow(() -> new NotFoundException("No user with id: " + id));

        log.info("Got user: " + person);

        return userMapper.userToUserDto(person);
    }

    @Override
    public void deleteUserById(Long id) {

        if (!userRepository.existsById(id)) throw new NotFoundException("No user with id: " + id);

        userRepository.deleteById(id);
        log.info("User with id " + id + " deleted.");
    }
}
