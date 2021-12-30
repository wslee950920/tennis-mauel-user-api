package com.tennismauel.user.web.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@Builder
@AllArgsConstructor
public class UpdateUserInfoRequest {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String nick;

    private String profile;

    @NotNull
    private Character gender;

    @Max(100)
    @Min(10)
    private Integer age;

    private String phone;
}
