package com.tennismauel.user.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        Boolean sex = true;
        String provider = "kakao";

        User savedUser = UserRepository.save(User.builder()
                .email(email)
                .nick(nick)
                .name(name)
                .sex(sex)
                .provider(provider).build());

        User selectedUser = UserRepository.getById(savedUser.getId());
        assertEquals(email, selectedUser.getEmail());
        assertEquals(nick, selectedUser.getNick());
        assertEquals(name, selectedUser.getName());
        assertEquals(sex, selectedUser.getSex());
        assertEquals(provider, selectedUser.getProvider());
    }

    @Test
    public void createUserFailNullable() {
        String email = "amicusadaras6@gmail.com";
        String nick = "qkdrnvhrrur";
        String name = "이우석";
        Boolean sex = null;
        String provider = null;

        assertThrows(DataIntegrityViolationException.class, () -> {
            UserRepository.save(User.builder()
                    .email(email)
                    .nick(nick)
                    .name(name)
                    .sex(sex)
                    .provider(provider).build());
        });
    }

    @Test
    public void createUserFailLength() {
        String email = "amicusadaras6@gmail.com".repeat(100);
        String nick = "qkdrnvhrrur";
        String name = "이우석";
        Boolean sex = true;
        String provider = "kakao";

        assertThrows(DataIntegrityViolationException.class, () -> {
            UserRepository.save(User.builder()
                    .email(email)
                    .nick(nick)
                    .name(name)
                    .sex(sex)
                    .provider(provider).build());
        });
    }
}
