package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.storage.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private long ID = 1;

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(UserDto userDto) {

        userDto.setId(ID++);

        User user = userMapper.userDtoToUser(userDto);

        userRepository.save(user);

        return userDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto) {

       if (userRepository.userNotExistById(userDto.getId())) throw new NotFoundException("No user with id: " + userDto.getId());
       log.info("Updated DTO: " + userDto);

        User user = userMapper.userDtoToUser(userDto);
        log.info("Saving user to DB: " + user);

        userRepository.save(user);

        return userDto;
    }

    @Override
    public UserDto getUserById(Long id)  {

        User user = userRepository.getUserById(id)
                .orElseThrow(() -> new NotFoundException("No user with id: " + id));

        log.info("Got user: " + user);

        return userMapper.userToUserDto(user);
    }

    @Override
    public void deleteUserById(Long id) {

        if (userRepository.userNotExistById(id)) throw new NotFoundException("No user with id: " + id);

        userRepository.deleteUserById(id);
        log.info("User with id " + id + " deleted.");
    }

}
