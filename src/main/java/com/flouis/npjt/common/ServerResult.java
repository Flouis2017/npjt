package com.flouis.npjt.common;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonSerialize
public class ServerResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String      msg;
    private Boolean     success;
    private Integer     total;
    private T           data;

    private ServerResult(String msg, Boolean success, T data){
        this.msg        = msg;
        this.success    = success;
        this.data       = data;
    }

    private ServerResult(){}

    public static ServerResult success(String msg){
        return new ServerResult(msg, true, null);
    }

    public static <T> ServerResult success(String msg, T data){
        return new ServerResult(msg, true, data);
    }

    public static ServerResult fail(String msg){
        return new ServerResult(msg, false, null);
    }

}
