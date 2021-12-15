package com.tennismauel.user.web.request;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("unit")
public class UserRegistrationDtoTest {
    private static Validator validator;

    @BeforeAll
    public static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

        validator = factory.getValidator();
    }

    @Test
    public void validateSuccess() {
        String email = "foo@bar";
        String nick = null;
        String name = null;
        String gender = null;
        String provider = "kakao";
        Integer agerange = null;
        String phone = null;
        String profile = null;
        String role = "ROLE_USER";

        UserRegistrationDto userRegistrationDto = UserRegistrationDto.builder()
                .email(email)
                .nick(nick)
                .name(name)
                .gender(gender)
                .provider(provider)
                .agerange(agerange)
                .phone(phone)
                .profile(profile)
                .role(role).build();

        Set<ConstraintViolation<UserRegistrationDto>> violations = validator.validate(userRegistrationDto);
        assertEquals(0, violations.size());
    }

    @Test
    public void validationFailNotEmail(){
        String email = "foo$bar";
        String nick = null;
        String name = null;
        String gender = null;
        String provider = "kakao";
        Integer agerange = null;
        String phone = null;
        String profile = null;
        String role = "ROLE_USER";

        UserRegistrationDto userRegistrationDto = UserRegistrationDto.builder()
                .email(email)
                .nick(nick)
                .name(name)
                .gender(gender)
                .provider(provider)
                .agerange(agerange)
                .phone(phone)
                .profile(profile)
                .role(role).build();

        Set<ConstraintViolation<UserRegistrationDto>> violations = validator.validate(userRegistrationDto);
        assertEquals(1, violations.size());
    }

    @Test
    public void validationFailNotNull(){
        String email = null;
        String nick = "qkdrnvhrrur";
        String name = "이우석";
        String gender = "male";
        String provider = null;
        Integer agerange = 20;
        String phone = "01020770883";
        String profile = "profile.jpg";
        String role = null;

        UserRegistrationDto userRegistrationDto = UserRegistrationDto.builder()
                .email(email)
                .nick(nick)
                .name(name)
                .gender(gender)
                .provider(provider)
                .agerange(agerange)
                .phone(phone)
                .profile(profile)
                .role(role).build();

        Set<ConstraintViolation<UserRegistrationDto>> violations = validator.validate(userRegistrationDto);
        assertEquals(3, violations.size());
    }

    @Test
    public void validationFailSize(){
        String email = "foo@bar";
        String nick = "qkdrnvhrrur".repeat(50);
        String name = "이우석";
        String gender = "male";
        String provider = "kakao";
        Integer agerange = 20;
        String profile = "profile.jpg";
        String phone = "01012345678";
        String role = "ROLE_USER";

        UserRegistrationDto userRegistrationDto = UserRegistrationDto.builder()
                .email(email)
                .nick(nick)
                .name(name)
                .gender(gender)
                .provider(provider)
                .agerange(agerange)
                .phone(phone)
                .profile(profile)
                .role(role).build();

        Set<ConstraintViolation<UserRegistrationDto>> violations = validator.validate(userRegistrationDto);
        assertEquals(1, violations.size());
    }
}
