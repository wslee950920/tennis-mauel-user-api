package com.tennismauel.user.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserInfoResponse {
    private String email;

    private String nick;

    private String profile;

    private Character gender;

    private Integer age;

    private String phone;
}
