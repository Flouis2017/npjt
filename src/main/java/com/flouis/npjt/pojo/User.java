package com.flouis.npjt.pojo;

import lombok.Data;

@Data
public class User {

    private Integer     id;
    private String      name;
    private Integer     gender;
    private Integer     age;
    private String      homeAddress;
    private String      createTime;
    private String      updateTime;

}
