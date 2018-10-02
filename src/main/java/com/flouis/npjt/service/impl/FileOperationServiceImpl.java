package com.flouis.npjt.service.impl;

import com.flouis.npjt.dao.FileOperationDao;
import com.flouis.npjt.service.FileOperationService;
import com.flouis.npjt.utils.PropertyUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * @author Flouis
 * @date 2018/10/02
 * @description
 **/

@Service
public class FileOperationServiceImpl implements FileOperationService {

    private String savePath = PropertyUtil.get("file.upload.path");

    @Autowired
    private FileOperationDao fileOperationDao;


    public List<Map<String,Object>> getFileList(){
        return this.fileOperationDao.getFileList();
    }


    public List<Map<String, Object>> saveFiles(MultipartHttpServletRequest request) throws Exception {
        List<Map<String, Object>> resList = Lists.newArrayList();

        // get parameters of files from frontend pages:
        List<MultipartFile> files = request.getFiles("files");
        int arrSize = files.size();
        String[] nameArr = new String[arrSize];
        String[] urlArr = new String[arrSize];
        String[] originalNameArr = new String[arrSize];
        MultipartFile file;

        // make dirs to save uploaded files:
        File dir = new File(savePath);
        if (!dir.exists()){
            dir.mkdirs();
        }

        // save files in the specified path:
        for (int i = 0; i < arrSize; i++){
            file = files.get(i);
            if (!file.isEmpty()){
                InputStream ins = file.getInputStream();

                // get extension:
                String originalFilename = file.getOriginalFilename();
                originalNameArr[i] = originalFilename;
                String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();

                // generate new filename:
                String newFilename = (System.currentTimeMillis() + i) + extension;
                nameArr[i] = newFilename;
                String url = savePath + "/" + newFilename;
                urlArr[i] = url;

                // read and write file:
                FileOutputStream fos = new FileOutputStream(url);
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = ins.read(buffer)) > 0){
                    fos.write(buffer, 0, len);
                }
                ins.close();
                fos.close();
            }
        }

        // file resource put in DB:

        for (int i=0; i < arrSize; i++){
            List<Object> args = Lists.newArrayList();
            args.add(urlArr[i]);
            args.add(nameArr[i]);
            args.add(originalNameArr[i]);
            String id = this.fileOperationDao.getKeyInsert(args.toArray());
            Map<String, Object> tmpMap = Maps.newHashMap();
            tmpMap.put("id", id);
            tmpMap.put("url", urlArr[i]);
            tmpMap.put("name", nameArr[i]);
            tmpMap.put("original_name", originalNameArr[i]);
            resList.add(tmpMap);
        }
        return resList;
    }

    public void download(HttpServletResponse response, String url) throws Exception {
        ServletOutputStream sos = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(url);
            response.setContentType("multipart/form-data");
            sos = response.getOutputStream();
            int len = 0;
            byte[] buffer = new byte[1024 * 10];
            while ( (len = fis.read(buffer)) != -1){
                sos.write(buffer, 0, len);
            }
            sos.flush();
        } catch (Exception e){
            e.printStackTrace();
            sos.flush();
            sos.close();
            fis.close();
        } finally {
            sos.close();
            fis.close();
        }
    }


    public int deleteById(String id){
        return this.fileOperationDao.deleteById(id);
    }

}
