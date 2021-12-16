package com.tennismauel.user.mapper;

import com.tennismauel.user.entity.User;
import com.tennismauel.user.web.request.RegistrationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RegistrationDtoToUser {
    @Mapping(target="id", ignore = true)
    User dtoToEntity(RegistrationDto registrationDto);
}
