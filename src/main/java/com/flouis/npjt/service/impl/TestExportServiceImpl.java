package com.flouis.npjt.service.impl;

import com.flouis.npjt.common.ServerResult;
import com.flouis.npjt.dao.ITestExportDao;
import com.flouis.npjt.service.ITestExportService;
import com.flouis.npjt.utils.ExcelHeader;
import com.flouis.npjt.utils.ExportUtil;
import com.flouis.npjt.utils.StringTool;
import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service("iTestExportService")
public class TestExportServiceImpl implements ITestExportService {

    @Autowired
    private ITestExportDao iTestExportDao;

    /**
     * @description: query data
     */
    public ServerResult getData(Map filterConditions) {

        List<Map<String, Object>> list = this.iTestExportDao.getUserMapList(filterConditions);

        return ServerResult.success("Export Success!", list);
    }


    /**
     * @description: export data to excels
     */
    public ResponseEntity<byte[]> exportExcel(HttpServletRequest request, Map filterConditions) throws IOException {
        // get data need to be exported:
        List<Map<String, Object>> exportList = this.iTestExportDao.getUserMapList(filterConditions);

        // set excel header:
        List<ExcelHeader> header = Lists.newArrayList();
        header.add(new ExcelHeader("id", "5"));
        header.add(new ExcelHeader("username", "10"));
        header.add(new ExcelHeader("gender", "10"));
        header.add(new ExcelHeader("age", "5"));
        header.add(new ExcelHeader("home address", "25"));

        // fill the excel body content:
        List<List<String>> body = Lists.newArrayList();
        for (Map<String, Object> rowItem : exportList){
            List<String> row = Lists.newArrayList();
            row.add(StringTool.toString( rowItem.get("id") ));
            row.add(StringTool.toString( rowItem.get("name") ));
            row.add(StringTool.toString( rowItem.get("gender") ));
            row.add(StringTool.toString( rowItem.get("age") ));
            row.add(StringTool.toString( rowItem.get("home_address") ));
            body.add(row);
        }

        Map<String, Object> result = ExportUtil.exportXLS(request, header, body, "User",
                ExportUtil.setFileDownloadFileName(request, "User") + ".xls");
        if (result != null){
            return new ResponseEntity<>(FileUtils.readFileToByteArray((File)result.get("file")),
                    (MultiValueMap<String, String>) result.get("headers"), HttpStatus.OK);
        } else {
            return null;
        }
    }


}
