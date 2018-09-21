package com.flouis.npjt.common;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

@Data
@JsonSerialize
public class ServerResult {

    private String      msg;
    private Boolean     success;
    private Integer     total;

    private ServerResult(String msg, Boolean success){
        this.msg        = msg;
        this.success    = success;
    }

    public static ServerResult success(String msg){
        return new ServerResult(msg, true);
    }

}
