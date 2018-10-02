package com.flouis.npjt.service;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface FileOperationService {

    List<Map<String,Object>> saveFiles(MultipartHttpServletRequest request) throws Exception;

    List<Map<String,Object>> getFileList();

    void download(HttpServletResponse response, String url) throws Exception;

    int deleteById(String id);
}
