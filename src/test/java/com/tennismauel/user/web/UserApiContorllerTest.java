package com.tennismauel.user.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tennismauel.user.service.UserService;
import com.tennismauel.user.service.exception.EmailExistException;
import com.tennismauel.user.web.request.RegistrationDto;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("unit")
@WebMvcTest
public class UserApiContorllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    public void registerUserSuccess() throws Exception {
        String email="foo@bar";
        String provider="naver";
        String role="ROLE_GUEST";

        RegistrationDto registrationDto = RegistrationDto.builder()
                .email(email)
                .provider(provider)
                .role(role).build();
        String content = objectMapper.writeValueAsString(registrationDto);

        doNothing().when(userService).register(isA(RegistrationDto.class));
        mockMvc.perform(post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isCreated());
    }

    @Test
    public void registerUserFailNotNull() throws Exception{
        RegistrationDto registrationDto = RegistrationDto.builder().build();
        String content=objectMapper.writeValueAsString(registrationDto);

        mockMvc.perform(post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void registerUserFailNotEmail() throws Exception{
        String email="foo$bar";
        String provider="naver";
        String role="ROLE_GUEST";

        RegistrationDto registrationDto = RegistrationDto.builder()
                .email(email)
                .provider(provider)
                .role(role).build();
        String content = objectMapper.writeValueAsString(registrationDto);

        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void registerUserFailEmailExist() throws Exception{
        String email="foo@bar";
        String provider="naver";
        String role="ROLE_GUEST";

        RegistrationDto registrationDto = RegistrationDto.builder()
                .email(email)
                .provider(provider)
                .role(role).build();
        String content = objectMapper.writeValueAsString(registrationDto);

        doThrow(EmailExistException.class).when(userService).register(isA(RegistrationDto.class));
        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isConflict());
    }
}
