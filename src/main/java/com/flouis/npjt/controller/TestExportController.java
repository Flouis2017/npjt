package com.flouis.npjt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Flouis
 * @date 2018/09/20
 * @description TODO
 **/

@Controller
@RequestMapping("/testExport")
public class TestExportController {


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String toTestPage(){
        System.out.println("Turn to test page");
        return "test";
    }
}
