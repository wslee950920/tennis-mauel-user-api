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
public class UpdateUserInfoRequestTest {
    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void validationSuccess(){
        String email="foo@bar";
        String nick="foo";
        String profile="profile.jpg";
        Character gender='M';
        Integer age=20;
        String phone="01012345678";

        UpdateUserInfoRequest info= UpdateUserInfoRequest.builder()
                .email(email)
                .nick(nick)
                .profile(profile)
                .gender(gender)
                .age(age)
                .phone(phone).build();

        Set<ConstraintViolation<UpdateUserInfoRequest>> violations=validator.validate(info);
        assertEquals(violations.size(), 0);
    }

    @Test
    public void validationFail_NotEmail_Blank_Null_Min(){
        String email="foobar";
        String nick=" ";
        String profile=null;
        Character gender=null;
        Integer age=9;
        String phone=null;

        UpdateUserInfoRequest info= UpdateUserInfoRequest.builder()
                .email(email)
                .nick(nick)
                .profile(profile)
                .gender(gender)
                .age(age)
                .phone(phone).build();

        Set<ConstraintViolation<UpdateUserInfoRequest>> violations=validator.validate(info);
        assertEquals(violations.size(), 4);
    }
}
