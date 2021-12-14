package com.tennismauel.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(length = 50, nullable = false)
    private String nick;

    @Column(length = 10, nullable = false)
    private String name;

    @Column(length = 100, nullable = true)
    private String profile;

    @Column(length = 10, nullable = false)
    private String gender;

    @Column(nullable = false)
    private Integer agerange;

    @Column(length = 10, nullable = false)
    private String provider;

    @Column(length = 20, nullable = true)
    private String phone;

    @Column(nullable = false)
    private String role;

    public User update(String nick, String profile, String phone, Integer agerange) {
        this.nick = nick;
        this.profile = profile;
        this.phone = phone;
        this.agerange = agerange;

        return this;
    }
}
