package org.example.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class User {
    
    private Integer id;

    /**
     * 用户账号
     */
    private String username;

    private String password;

    private String nickname;

    private Date birthday;

    private String phoneNumber;

    private String email;

    private String head;
}