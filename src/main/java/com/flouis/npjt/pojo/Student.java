package com.flouis.npjt.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Student {

    private String      id;

    private String      name;

    private Integer     age;

    private Integer     gender;

    private String      homeAddress;

    private Date        birthday;

}
