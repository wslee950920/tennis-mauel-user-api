package com.tennismauel.user.service;

import com.tennismauel.user.entity.User;
import com.tennismauel.user.mapper.RegistrationDtoToUser;
import com.tennismauel.user.repository.UserRepository;
import com.tennismauel.user.service.exception.EmailExistException;
import com.tennismauel.user.service.exception.RegistrationException;
import com.tennismauel.user.web.request.RegistrationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final RegistrationDtoToUser RegistrationDtoToUser;

    @Override
    public void register(RegistrationDto registrationDto) throws RegistrationException {
        Optional<User> user=userRepository.findByEmail(registrationDto.getEmail());
        if(user.isPresent()){
            throw new EmailExistException();
        }

        User newUser=RegistrationDtoToUser.dtoToEntity(registrationDto);
        userRepository.save(newUser);
    }
}
