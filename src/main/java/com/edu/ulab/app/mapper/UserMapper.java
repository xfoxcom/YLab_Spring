package com.edu.ulab.app.mapper;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.Person;
import com.edu.ulab.app.web.request.UserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto userRequestToUserDto(UserRequest userRequest);

    UserRequest userDtoToUserRequest(UserDto userDto);

    @Mapping(target = "booksIds", source = "booksIds")
    Person userDtoToUser(UserDto userDto);

    @Mapping(target = "booksIds", source = "booksIds")
    UserDto userToUserDto(Person person);

}
