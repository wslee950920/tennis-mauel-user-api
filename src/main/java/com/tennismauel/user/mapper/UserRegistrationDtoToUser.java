package com.tennismauel.user.mapper;

import com.tennismauel.user.entity.User;
import com.tennismauel.user.web.request.UserRegistrationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserRegistrationDtoToUser {
    @Mapping(target="id", ignore = true)
    User dtoToEntity(UserRegistrationDto userRegistrationDto);
}
