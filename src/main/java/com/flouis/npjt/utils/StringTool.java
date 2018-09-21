package com.flouis.npjt.utils;

public class StringTool {

    public static boolean isNotEmpty(Object obj){
        if (obj == null){
            return false;
        }
        if ("".equals(String.valueOf(obj))){
            return false;
        }
        return true;
    }

    public static boolean isEmpty(Object obj){
        if (obj == null){
            return true;
        }
        if ("".equals(String.valueOf(obj))){
            return true;
        }
        return false;
    }

}
