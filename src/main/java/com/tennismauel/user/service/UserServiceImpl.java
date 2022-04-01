package com.tennismauel.user.service;

import com.tennismauel.user.entity.User;
import com.tennismauel.user.mapper.UserInfoMapper;
import com.tennismauel.user.repository.UserRepository;
import com.tennismauel.user.web.exception.EmailNotExistException;
import com.tennismauel.user.web.request.UpdateUserInfoRequest;
import com.tennismauel.user.web.response.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final UserInfoMapper userInfoMapper;

    @Override
    public UserInfoResponse getUserInfo(String email) throws EmailNotExistException {
        Optional<User> optional=userRepository.findByEmail(email);
        if(optional.isEmpty()){
            throw new EmailNotExistException();
        }

        User user=optional.get();
        return userInfoMapper.userToInfoDto(user);
    }

    @Override
    public void updateUserInfo(UpdateUserInfoRequest info) throws EmailNotExistException{
        log.debug(info.toString());

        Optional<User> opt=userRepository.findByEmail(info.getEmail());
        if(opt.isEmpty()){
            throw new EmailNotExistException();
        }

        User user=opt.get();
        user.updateNick(info.getNick());
        user.updateProfile(info.getProfile());
        user.updateAge(info.getAge());
        user.updateGender(info.getGender());
        user.updatePhone(info.getPhone());

        userRepository.save(user);
    }
}
