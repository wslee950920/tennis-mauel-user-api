package com.tennismauel.user.service;

import com.tennismauel.user.web.exception.EmailNotExistException;
import com.tennismauel.user.web.request.UpdateUserInfoRequest;
import com.tennismauel.user.web.response.UserInfoResponse;

public interface UserService {
    UserInfoResponse getUserInfo(String email) throws EmailNotExistException;

    void updateUserInfo(UpdateUserInfoRequest infoDto) throws EmailNotExistException;
}
