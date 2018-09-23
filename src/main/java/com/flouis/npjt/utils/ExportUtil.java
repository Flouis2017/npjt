package com.flouis.npjt.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Flouis
 * @date 2018/09/23
 * @description TODO
 **/
public class ExportUtil {

    private static ResourceBundle bundle = ResourceBundle.getBundle("config.dev");

    public final static String HEAD_NAME = "headName";

    public final static String HEAD_SIZE = "headSize";

    /**
     * @param request
     * @param header
     * @param body
     * @param sheetFileName
     * @param fileName
     * @return Excel File
     */
    public static Map<String, Object> exportXLS(HttpServletRequest request, final List<ExcelHeader> header,
           final List<List<String>> body, String sheetFileName, String fileName) {
        try {
            String os = System.getProperty("os.name");
            String tempString = null;
            if (os.indexOf("Windows") > -1) {
                tempString = request.getServletContext().getRealPath(bundle.getString("uploadTempPath"));
            } else {
                String shareFolder = bundle.getString("shared.folder");
                tempString = shareFolder + request.getContextPath() + bundle.getString("uploadTempPath");
            }
//            System.out.println( "tempString: " + tempString );
            File tempFile = new File(tempString);
            if (!tempFile.exists() && !tempFile.isDirectory()) {
                tempFile.mkdirs();
            }

            HSSFWorkbook workbook = export(header, body, sheetFileName);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            headers.setContentDispositionFormData("attachment", new String(fileName.getBytes("UTF-8"), "ISO8859-1"));
            String uploadPath = tempString + "/" + fileName;
            File file = new File(uploadPath);
            FileOutputStream stream = new FileOutputStream(file);
            workbook.write(stream);
            HashMap<String, Object> map = new HashMap();
            map.put("file", file);
            map.put("headers", headers);
            map.put("tempFilePath", uploadPath);
            stream.close();
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @description: map data from List into excel
     * @param header
     * @param body
     * @param sheetFileName
     * @return
     */
    public static HSSFWorkbook export(final List<ExcelHeader> header, final List<List<String>> body, final String sheetFileName) {
        HSSFWorkbook workbook = new HSSFWorkbook();

        // generate sheet and Excel header:
        HSSFSheet sheet = workbook.createSheet(sheetFileName);
        HSSFRow headerRow = sheet.createRow(0);
        for (int i = 0; i < header.size(); i++){
            headerRow.createCell(i).setCellValue(header.get(i).getHeadName());
            sheet.setColumnWidth(i, Integer.valueOf(header.get(i).getHeadSize()) * 256 );
        }

        // fill content:
        int rowNum = 1;
        for (List<String> rowItem : body){
            HSSFRow row = sheet.createRow(rowNum++);
            for (int i = 0; i < rowItem.size(); i++){
                row.createCell(i).setCellValue(rowItem.get(i));
            }
        }
        return workbook;
    }

    public static String setFileDownloadFileName(HttpServletRequest request, String fileName){
        final String userAgent = request.getHeader("USER-AGENT");
        String finalFileName = "";
        try {
            if (StringUtils.contains(userAgent, "MSIE")) {// IE浏览器
                finalFileName = URLEncoder.encode(fileName, "UTF8");
            } else {
                finalFileName = fileName;
            }
        } catch (UnsupportedEncodingException e) {
        }
        return finalFileName;
    }

}
