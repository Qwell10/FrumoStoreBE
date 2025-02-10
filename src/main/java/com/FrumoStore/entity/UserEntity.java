package com.FrumoStore.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_entity")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @SequenceGenerator(
            name = "userEntity_sequence",
            sequenceName = "userEntity_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "userEntity_sequence"
    )
    private long id;
    private String nickname;
    private String password;

    public UserEntity(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }
}