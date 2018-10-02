package com.flouis.npjt.controller;

import com.flouis.npjt.common.ServerResult;
import com.flouis.npjt.service.FileOperationService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author Flouis
 * @date 2018/10/02
 * @description Controller to test file upload & download
 **/

@Controller
@RequestMapping("/fileOperation")
public class FileOperationController {

    @Autowired
    private FileOperationService fileOperationService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String toFileOperationPage(){
        return "fileOperation";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ServerResult initPage(){
        Map<String, Object> originalMap = Maps.newHashMap();
        // get file resource list:
        List<Map<String, Object>> fileList = this.fileOperationService.getFileList();
        originalMap.put("fileList", fileList);
        return ServerResult.success(originalMap);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public ServerResult upload(MultipartHttpServletRequest request){
        try{
            List<Map<String, Object>> resList = this.fileOperationService.saveFiles(request);
            return ServerResult.success("文件上传成功", resList);
        } catch (Exception e){
            e.printStackTrace();
        }
        return ServerResult.fail("文件上传失败!");
    }


    @RequestMapping(value = "/showOrDownload/{originalName}", method = RequestMethod.GET)
    public void showOrDownload(HttpServletRequest request, HttpServletResponse response){
        String url = request.getParameter("url");
        try {
            this.fileOperationService.download(response, url);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public ServerResult delete(HttpServletRequest request){
        String id = request.getParameter("id");
        int cnt = this.fileOperationService.deleteById(id);
        if ( cnt > 0 ){
            return ServerResult.success("deleted file record successfully!");
        }
        return ServerResult.success("deleted file record unsuccessfully!");
    }

}
