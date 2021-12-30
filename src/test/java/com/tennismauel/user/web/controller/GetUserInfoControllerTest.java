package com.tennismauel.user.web.controller;

import com.tennismauel.user.service.UserService;
import com.tennismauel.user.web.response.UserInfoResponse;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;

@Tag("unit")
@WebMvcTest
public class GetUserInfoControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtDecoder jwtDecoder;

    @Test
    public void getUserInfoTestSuccess() throws Exception {
        String email = "foo@bar";

        UserInfoResponse info = UserInfoResponse.builder()
                .email(email).build();

        when(userService.getUserInfo(email)).thenReturn(info);
        mvc.perform(get("/@user/info")
                        .with(jwt().jwt(jwt ->
                                jwt
                                        .claim("username", email))
                                .authorities(new SimpleGrantedAuthority("ROLE_GUEST"))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email));
    }

    @Test
    public void getUserInfoFail_NotAuthorized() throws Exception {
        String email = "foo@bar";

        UserInfoResponse info = UserInfoResponse.builder()
                .email(email).build();

        when(userService.getUserInfo(email)).thenReturn(info);

        mvc.perform(get("/@user/info")
                        .with(jwt().jwt(jwt -> jwt.claim("username", email))))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void getUserInfoFail_NotAuthenticated() throws Exception {
        String email = "foo@bar";

        UserInfoResponse info = UserInfoResponse.builder()
                .email(email).build();

        when(userService.getUserInfo(email)).thenReturn(info);

        mvc.perform(get("/@user/info"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}