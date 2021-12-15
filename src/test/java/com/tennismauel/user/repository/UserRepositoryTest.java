package com.tennismauel.user.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.tennismauel.user.entity.User;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

@Tag("unit")
//@ExtendWith(SpringExtension.class)가 @DataJpaTest에 포함 됨
@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    UserRepository UserRepository;

    @Test
    public void createUserSuccess() {
        String email = "foo@bar";
        String nick = null;
        String name = null;
        String gender = null;
        String provider = "kakao";
        Integer agerange = null;
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
        String email = null;
        String nick = "qkdrnvhrrur";
        String name = "이우석";
        String gender = "male";
        String provider = null;
        Integer agerange = 20;
        String phone = "01020770883";
        String profile = "profile.jpg";
        String role = null;

        assertThrows(DataIntegrityViolationException.class, () -> {
            UserRepository.save(User.builder()
                    .email(email)
                    .nick(nick)
                    .name(name)
                    .agerange(agerange)
                    .phone(phone)
                    .profile(profile)
                    .gender(gender)
                    .role(role)
                    .provider(provider).build());
        });
    }

    @Test
    public void createUserFailLength() {
        String email = "foo@bar";
        String nick = "qkdrnvhrrur".repeat(50);
        String name = "이우석";
        String gender = "male";
        String provider = "kakao";
        Integer agerange = 20;
        String profile = "profile.jpg";
        String phone = "01012345678";
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
