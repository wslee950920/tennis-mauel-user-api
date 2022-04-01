package com.tennismauel.user.web;

import com.tennismauel.user.service.UserService;
import com.tennismauel.user.web.request.UpdateUserInfoRequest;
import com.tennismauel.user.web.response.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/@user")
public class UserController {
    private final UserService userService;

    @GetMapping(path = "/info")
    public ResponseEntity<UserInfoResponse> getUserInfo(@AuthenticationPrincipal Jwt principal){
        log.debug(principal.getClaims().toString());

        String email=principal.getClaimAsString("username");
        UserInfoResponse info=userService.getUserInfo(email);

        return ResponseEntity.ok(info);
    }

    @PutMapping(path = "/update")
    @PreAuthorize("#principal!=null && #principal.claims['username'] eq #infoRequest.email")
    public ResponseEntity updateUserInfo(@AuthenticationPrincipal Jwt principal, @Valid @RequestBody UpdateUserInfoRequest infoRequest, HttpServletResponse response){
        userService.updateUserInfo(infoRequest);

        Cookie token=new Cookie("access_token", null);
        token.setMaxAge(0);
        token.setPath("/");

        response.addCookie(token);

        return ResponseEntity.noContent().build();
    }
}
