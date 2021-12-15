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

    @Column(length = 50, nullable = true)
    private String nick;

    @Column(length = 10, nullable = true)
    private String name;

    @Column(length = 100, nullable = true)
    private String profile;

    @Column(length = 10, nullable = true)
    private String gender;

    @Column(nullable = true)
    private Integer agerange;

    @Column(length = 10, nullable = false)
    private String provider;

    @Column(length = 20, nullable = true)
    private String phone;

    @Column(length=20, nullable = false)
    private String role;
}
