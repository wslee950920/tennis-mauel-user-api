package com.tennismauel.user.service;

import com.tennismauel.user.entity.User;
import com.tennismauel.user.mapper.UserRegistrationDtoToUser;
import com.tennismauel.user.repository.UserRepository;
import com.tennismauel.user.service.exception.EmailExistException;
import com.tennismauel.user.service.exception.RegistrationException;
import com.tennismauel.user.web.request.UserRegistrationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final UserRegistrationDtoToUser userRegistrationDtoToUser;

    @Override
    public void register(UserRegistrationDto userRegistrationDto) throws RegistrationException {
        Optional<User> user=userRepository.findByEmail(userRegistrationDto.getEmail());
        if(user.isPresent()){
            throw new EmailExistException();
        }

        User newUser=userRegistrationDtoToUser.dtoToEntity(userRegistrationDto);
        userRepository.save(newUser);
    }
}
