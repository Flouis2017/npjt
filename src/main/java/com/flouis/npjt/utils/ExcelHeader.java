package com.flouis.npjt.utils;

import lombok.Data;

/**
 * @author Flouis
 * @date 2018/09/23
 * @description TODO
 **/

@Data
public class ExcelHeader {

    private String headName;
    private String headSize;

    public ExcelHeader(){}

    public ExcelHeader(String headName, String headSize){
        this.headName = headName;
        this.headSize = headSize;
    }

}
