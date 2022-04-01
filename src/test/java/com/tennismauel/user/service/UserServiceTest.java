package com.tennismauel.user.service;

import com.tennismauel.user.entity.Role;
import com.tennismauel.user.entity.User;
import com.tennismauel.user.mapper.UserInfoMapper;
import com.tennismauel.user.repository.UserRepository;
import com.tennismauel.user.web.exception.EmailNotExistException;
import com.tennismauel.user.web.response.UserInfoResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserInfoMapper userInfoMapper;

    @Test
    public void getUserInfoSuccess(){
        String email="foo@bar";
        Role role=Role.GUEST;
        String provider="naver";

        User user=User.builder()
                .email(email)
                .role(role)
                .provider(provider).build();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        UserInfoResponse info= UserInfoResponse.builder()
                .email(email).build();

        when(userInfoMapper.userToInfoDto(user)).thenReturn(info);

        UserInfoResponse response=userService.getUserInfo(user.getEmail());
        assertEquals(user.getEmail(), response.getEmail());
    }

    @Test
    public void getUserInfoFail_EmailNotExist(){
        String email="foo@bar";

        when(userRepository.findByEmail(email)).thenReturn(Optional.ofNullable(null));

        assertThrows(EmailNotExistException.class, ()->{
            userService.getUserInfo(email);
        });
    }
}
