package com.tennismauel.user.web;

import com.tennismauel.user.service.UserService;
import com.tennismauel.user.service.exception.EmailExistException;
import com.tennismauel.user.service.exception.RegistrationException;
import com.tennismauel.user.web.request.RegistrationDto;
import com.tennismauel.user.web.response.ApiResult;
import com.tennismauel.user.web.response.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserApiController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResult> register(@Valid @RequestBody RegistrationDto registrationDto){
        log.debug("register endpoint");

        try{
            userService.register(registrationDto);
        } catch (RegistrationException e) {
            if (e instanceof EmailExistException) {
                return Result.conflict();
            }
        }

        return Result.created();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResult> handleValidationExceptions(MethodArgumentNotValidException ex){
        return Result.failure("validation failed");
    }
}
