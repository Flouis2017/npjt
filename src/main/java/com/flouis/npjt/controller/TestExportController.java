package com.flouis.npjt.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.flouis.npjt.common.ServerResult;
import com.flouis.npjt.service.ITestExportService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
//import net.sf.json.JSONObject;
//import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;


/**
 * @author Flouis
 * @date 2018/09/20
 * @description TODO
 **/

@Controller
@RequestMapping("/testExport")
public class TestExportController {

    @Autowired
    private ITestExportService iTestExportService;


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String toTestPage(){
        return "test";
    }

    @RequestMapping(value = "/exportExcel", method = RequestMethod.POST)
    @ResponseBody
    public ServerResult exportExcel(HttpServletRequest request){
//      System.out.println(request.getParameter("paramsStr")); // {"name":"林田惠","gender":"","age":""}
//      parse param string from frontend:
        Map filterConditions = JSONObject.parseObject( request.getParameter("paramsStr") );
        ServerResult serverResult = this.iTestExportService.exportExcel(filterConditions);
        return serverResult;
    }


    @RequestMapping(value = "/exportExcel2", method = RequestMethod.POST)
    public void exportExcel2(HttpServletRequest request, HttpServletResponse response){
        System.out.println(request.getParameter("paramsStr"));
        Map<String, Object> resMap = Maps.newHashMap();
        resMap.put("success", false);
        resMap.put("msg", "Export Failed!");
        resMap.put("total", null);
        List<Map> list = Lists.newArrayList();
        list.add(resMap);

//      将Map/List转为json格式的对象——等同于@JsonSerialize注解的功能(Convert map to a json Object)
//      不过需要注意的是使用fastjson会忽略值为null的键值对的转化，使用net.sf.json则不会。
//      JSONObject json = JSONObject.fromObject(resMap);  // {"msg":"Export Failed!","total":null,"success":false}
//      JSONArray jsonArray = JSONArray.fromObject(list); // [{"msg":"Export Failed!","total":null,"success":false}]
        Object json = JSONObject.toJSON(resMap);            // {"msg":"Export Failed!","success":false}
        Object jsonArray = JSONArray.toJSON(list);          // [{"msg":"Export Failed!","success":false}]
        System.out.println("json: " + json.toString());
        System.out.println("jsonArray: " + jsonArray.toString());

//      将json格式的对象返回给前端页面——等同于@RespsoneBody注解的功能(write json data to frontend)
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = null;
        try{
            writer = response.getWriter();
        } catch (Exception e){
            System.out.println("Exception: " + e.getMessage());
        } finally {
            writer.print(json);
            writer.flush();
            writer.close();
        }
    }

}
