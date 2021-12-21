package com.tennismauel.user.mapper;

import com.tennismauel.user.entity.User;
import com.tennismauel.user.web.request.RegistrationDto;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("unit")
@SpringBootTest(classes = {RegistrationDtoToUserImpl.class})
public class RegistrationDtoToUserTest {
    @Autowired
    RegistrationDtoToUser registrationDtoToUser;

    @Test
    public void dtoToEntitySuccess(){
        String email = "foo@bar";
        String nick = null;
        String name = null;
        String gender = null;
        String provider = "kakao";
        Integer agerange = null;
        String phone = null;
        String profile = null;
        String role = "ROLE_USER";

        RegistrationDto registrationDto = RegistrationDto.builder()
                .email(email)
                .nick(nick)
                .name(name)
                .gender(gender)
                .provider(provider)
                .agerange(agerange)
                .phone(phone)
                .profile(profile)
                .role(role).build();

        User user=registrationDtoToUser.dtoToEntity(registrationDto);
        assertEquals(email, user.getEmail());
        assertEquals(nick, user.getNick());
        assertEquals(name, user.getName());
        assertEquals(gender, user.getGender());
        assertEquals(provider, user.getProvider());
        assertEquals(agerange, user.getAgerange());
        assertEquals(phone, user.getPhone());
        assertEquals(profile, user.getProfile());
        assertEquals(role, user.getRole());
    }

    @Test
    public void dtoToEntitySuccessNullable(){
        RegistrationDto registrationDto = RegistrationDto.builder().build();

        //레포지토리에 저장되기 전까지는 nullable=false에 null값 들어가도 괜찮은 듯
        User user=registrationDtoToUser.dtoToEntity(registrationDto);
        assertEquals(null, user.getEmail());
        assertEquals(null, user.getNick());
        assertEquals(null, user.getName());
        assertEquals(null, user.getGender());
        assertEquals(null, user.getProvider());
        assertEquals(null, user.getAgerange());
        assertEquals(null, user.getPhone());
        assertEquals(null, user.getProfile());
        assertEquals(null, user.getRole());
    }
}
