package com.tennismauel.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        uniqueConstraints={
                @UniqueConstraint(
                        columnNames={"email"}
                ),
                @UniqueConstraint(
                        columnNames={"nick"}
                )
        }
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 50)
    private String nick;

    @Column(length = 300)
    private String profile;

    @Column(nullable = true)
    private Character gender;

    @Column(nullable = true)
    private Integer age;

    @Column(length = 20, nullable = true)
    private String phone;

    @Column(length = 10, nullable = false)
    private String provider;

    @Enumerated(EnumType.STRING)
    @Column(length=20, nullable = false)
    private Role role;

    public void updateNick(String nick){
        this.nick=nick;
    }

    public void updateProfile(String profile){
        this.profile=profile;
    }

    public void updateAge(Integer age){
        this.age=age;
    }

    public void updateGender(Character gender){
        this.gender=gender;
    }

    public void updatePhone(String phone){
        this.phone=phone;
    }
}
