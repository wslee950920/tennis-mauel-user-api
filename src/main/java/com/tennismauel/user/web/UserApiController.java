package com.tennismauel.user.web;

import com.tennismauel.user.service.UserService;
import com.tennismauel.user.service.exception.EmailExistException;
import com.tennismauel.user.service.exception.RegistrationException;
import com.tennismauel.user.web.dto.UserRegistrationDto;
import com.tennismauel.user.web.results.ApiResult;
import com.tennismauel.user.web.results.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserApiController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResult> register(@Valid @RequestBody UserRegistrationDto userRegistrationDto){
        try{
            userService.register(userRegistrationDto);
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
