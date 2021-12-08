package com.tennismauel.user.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

//더 이상 @ExtendWith(SpringExtension.class)어노테이션을 써줄 필요 없다. @DataJpaTest에 포함 됨
@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    UserRepository UserRepository;

    @Test
    public void createUser() {
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
}
