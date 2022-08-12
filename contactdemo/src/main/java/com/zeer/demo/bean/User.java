package com.zeer.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    private long id;

    private String phoneNumber;
    private String password;
    private String username;
    private String studentID;



}
