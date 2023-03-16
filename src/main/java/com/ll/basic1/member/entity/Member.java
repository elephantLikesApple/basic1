package com.ll.basic1.member.entity;

import lombok.*;

@AllArgsConstructor
@Getter
@ToString
public class Member {
    private long id;
    private String username;
    private String password;
}
