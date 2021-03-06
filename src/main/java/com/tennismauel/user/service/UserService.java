package com.tennismauel.user.service;

import com.tennismauel.user.service.exception.RegistrationException;
import com.tennismauel.user.web.dto.UserRegistrationDto;

public interface UserService {
    public void register(UserRegistrationDto userRegistrationDto) throws RegistrationException;
}
