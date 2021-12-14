package com.tennismauel.user.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.tennismauel.user.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

//@ExtendWith(SpringExtension.class)가 @DataJpaTest에 포함 됨
@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    UserRepository UserRepository;

    @Test
    public void createUserSuccess() {
        String email = "amicusadaras6@gmail.com";
        String nick = "qkdrnvhrrur";
        String name = "이우석";
        String gender = "male";
        String provider = "kakao";
        Integer agerange = 20;
        String phone = null;
        String profile = null;
        String role = "ROLE_USER";

        User savedUser = UserRepository.save(User.builder()
                .email(email)
                .nick(nick)
                .name(name)
                .gender(gender)
                .agerange(agerange)
                .phone(phone)
                .role(role)
                .profile(profile)
                .provider(provider).build());

        User selectedUser = UserRepository.getById(savedUser.getId());
        assertEquals(email, selectedUser.getEmail());
        assertEquals(nick, selectedUser.getNick());
        assertEquals(name, selectedUser.getName());
        assertEquals(gender, selectedUser.getGender());
        assertEquals(provider, selectedUser.getProvider());
        assertEquals(agerange, selectedUser.getAgerange());
        assertEquals(phone, selectedUser.getPhone());
        assertEquals(profile, selectedUser.getProfile());
        assertEquals(role, selectedUser.getRole());
    }

    @Test
    public void createUserFailNullable() {
        String email = "amicusadaras6@gmail.com";
        String nick = "qkdrnvhrrur";
        String name = "이우석";
        Integer agerange = 20;
        String gender = null;
        String provider = null;
        String role = null;

        assertThrows(DataIntegrityViolationException.class, () -> {
            UserRepository.save(User.builder()
                    .email(email)
                    .nick(nick)
                    .name(name)
                    .agerange(agerange)
                    .gender(gender)
                    .role(role)
                    .provider(provider).build());
        });
    }

    @Test
    public void createUserFailLength() {
        String email = "amicusadaras6@gmail.com".repeat(100);
        String nick = "qkdrnvhrrur";
        String name = "이우석";
        String gender = "male";
        String provider = "kakao";
        Integer agerange = 20;
        String profile = null;
        String phone = null;
        String role = "ROLE_USER";

        assertThrows(DataIntegrityViolationException.class, () -> {
            UserRepository.save(User.builder()
                    .email(email)
                    .nick(nick)
                    .name(name)
                    .agerange(agerange)
                    .gender(gender)
                    .phone(phone)
                    .profile(profile)
                    .role(role)
                    .provider(provider).build());
        });
    }
}
