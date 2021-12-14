package com.tennismauel.user.service;

import com.tennismauel.user.service.exception.RegistrationException;
import com.tennismauel.user.web.dto.UserRegistrationDto;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Override
    public void register(UserRegistrationDto userRegistrationDto) throws RegistrationException {

    }
}
