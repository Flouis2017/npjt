package com.flouis.npjt.utils;

import java.util.ResourceBundle;

/**
 * @author Flouis
 * @date 2018/10/02
 * @description
 **/
public class PropertyUtil {

    private static ResourceBundle bundle = ResourceBundle.getBundle("config.dev");

    public static String get(String key){
        return bundle.getString(key);
    }

}
