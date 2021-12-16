package com.tennismauel.user.service;

import com.tennismauel.user.service.exception.RegistrationException;
import com.tennismauel.user.web.request.RegistrationDto;

public interface UserService {
    public void register(RegistrationDto registrationDto) throws RegistrationException;
}
