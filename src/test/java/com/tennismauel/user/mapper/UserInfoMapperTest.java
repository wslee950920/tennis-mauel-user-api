package com.tennismauel.user.mapper;

import com.tennismauel.user.entity.Role;
import com.tennismauel.user.entity.User;
import com.tennismauel.user.web.response.UserInfoResponse;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("unit")
public class UserInfoMapperTest {
    private UserInfoMapper mapper= Mappers.getMapper(UserInfoMapper.class);

    @Test
    public void mappingUserToUserInfoDto(){
        String email = "foo@bar";
        String nick = "foo";
        Character gender = 'M';
        Integer age = 20;
        String phone = "01012345678";
        String profile = "profile.jpg";
        String provider = "kakao";
        Role role = Role.MEMBER;

        User user = User.builder()
                .email(email)
                .nick(nick)
                .gender(gender)
                .age(age)
                .phone(phone)
                .role(role)
                .profile(profile)
                .provider(provider).build();

        UserInfoResponse infoDTO=mapper.userToInfoDto(user);

        assertEquals(user.getEmail(), infoDTO.getEmail());
        assertEquals(user.getNick(), infoDTO.getNick());
        assertEquals(user.getGender(), infoDTO.getGender());
        assertEquals(user.getAge(), infoDTO.getAge());
        assertEquals(user.getPhone(), infoDTO.getPhone());
        assertEquals(user.getProfile(), infoDTO.getProfile());
    }
}
