package com.flouis.npjt.service.impl;

import com.flouis.npjt.common.ServerResult;
import com.flouis.npjt.dao.ITestExportDao;
import com.flouis.npjt.service.ITestExportService;
import com.flouis.npjt.utils.SqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("iTestExportService")
public class TestExportServiceImpl implements ITestExportService {

    @Autowired
    private ITestExportDao iTestExportDao;

    /**
     * @description: export data from table test_user to excel
     */
    public ServerResult exportExcel(Map filterConditions) {

        List<Map<String, Object>> list = this.iTestExportDao.getUserMapList(filterConditions);

        return ServerResult.success("Export Success!", list);
    }
}
