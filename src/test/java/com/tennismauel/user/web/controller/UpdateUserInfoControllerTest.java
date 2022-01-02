package com.tennismauel.user.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tennismauel.user.service.UserService;
import com.tennismauel.user.web.request.UpdateUserInfoRequest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("unit")
@WebMvcTest
public class UpdateUserInfoControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtDecoder jwtDecoder;

    @Test
    public void updateUserInfoSuccess() throws Exception {
        String email="foo@bar";
        String nick="foo";
        Character gender='M';
        Integer age=20;

        UpdateUserInfoRequest userInfoRequest=UpdateUserInfoRequest.builder()
                .email(email)
                .nick(nick)
                .gender(gender)
                .age(age).build();
        String content= objectMapper.writeValueAsString(userInfoRequest);

        doNothing().when(userService).updateUserInfo(userInfoRequest);

        mvc.perform(put("/@user/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .with(jwt().jwt(jwt->
                                        jwt.claim("username", email))
                                .authorities(new SimpleGrantedAuthority("ROLE_MEMBER"))))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(cookie().value("access_token", (String) null))
                .andExpect(cookie().maxAge("access_token", 0));
    }

    @Test
    public void updateUserInfoFail_NotEmail() throws Exception {
        String email="foobar";
        String nick="foo";
        Character gender='M';
        Integer age=20;

        UpdateUserInfoRequest userInfoRequest=UpdateUserInfoRequest.builder()
                .email(email)
                .nick(nick)
                .gender(gender)
                .age(age).build();
        String content= objectMapper.writeValueAsString(userInfoRequest);

        doNothing().when(userService).updateUserInfo(userInfoRequest);

        mvc.perform(put("/@user/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .with(jwt().jwt(jwt->
                                        jwt.claim("username", email))
                                .authorities(new SimpleGrantedAuthority("ROLE_MEMBER"))))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateUserInfoFail_UnAuthenticated() throws Exception {
        String email="foo@bar";
        String nick="foo";
        Character gender='M';
        Integer age=20;

        UpdateUserInfoRequest userInfoRequest=UpdateUserInfoRequest.builder()
                .email(email)
                .nick(nick)
                .gender(gender)
                .age(age).build();
        String content= objectMapper.writeValueAsString(userInfoRequest);

        doNothing().when(userService).updateUserInfo(userInfoRequest);

        mvc.perform(put("/@user/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void updateUserInfoFail_UnAuthorized() throws Exception {
        String email="foo@bar";
        String nick="foo";
        Character gender='M';
        Integer age=20;

        UpdateUserInfoRequest userInfoRequest=UpdateUserInfoRequest.builder()
                .email(email)
                .nick(nick)
                .gender(gender)
                .age(age).build();
        String content= objectMapper.writeValueAsString(userInfoRequest);

        doNothing().when(userService).updateUserInfo(userInfoRequest);

        mvc.perform(put("/@user/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .with(jwt().jwt(jwt->
                                        jwt.claim("username", email))))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void updateUserInfoFail_NotOwner() throws Exception {
        String email="foo@bar";
        String nick="foo";
        Character gender='M';
        Integer age=20;

        UpdateUserInfoRequest userInfoRequest=UpdateUserInfoRequest.builder()
                .email(email)
                .nick(nick)
                .gender(gender)
                .age(age).build();
        String content= objectMapper.writeValueAsString(userInfoRequest);

        doNothing().when(userService).updateUserInfo(userInfoRequest);

        mvc.perform(put("/@user/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .with(jwt().jwt(jwt->
                                        jwt.claim("username", "bar@foo"))
                                .authorities(new SimpleGrantedAuthority("ROLE_MEMBER"))))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
