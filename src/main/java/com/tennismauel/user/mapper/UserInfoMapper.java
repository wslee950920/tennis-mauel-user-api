package com.tennismauel.user.mapper;

import com.tennismauel.user.entity.User;
import com.tennismauel.user.web.response.UserInfoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserInfoMapper {
    UserInfoResponse userToInfoDto(User user);
}
