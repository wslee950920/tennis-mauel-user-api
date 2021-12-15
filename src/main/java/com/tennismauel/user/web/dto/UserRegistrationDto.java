package com.tennismauel.user.web.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserRegistrationDto {
    @Size(max = 50)
    @Email
    @NotNull(message = "email not null")
    private String email;

    @Size(max = 50)
    private String nick;

    @Size(max = 10)
    private String name;

    @Size(max = 100)
    private String profile;

    @Size(max = 10)
    private String gender;

    private Integer agerange;

    @Size(max = 10)
    @NotNull(message = "provider not null")
    private String provider;

    @Size(max = 20)
    private String phone;

    @Size(max = 20)
    @NotNull(message = "role not null")
    private String role;
}
