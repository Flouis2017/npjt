package com.flouis.npjt.dao.impl;

import com.flouis.npjt.dao.ITestExportDao;
import com.flouis.npjt.utils.SqlUtil;
import com.flouis.npjt.utils.StringTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("iTestExportDao")
public class TestExportDaoImpl implements ITestExportDao {

    @Autowired
    private SqlUtil sqlUtil;

    /**
     * @description: get
     */
    public List<Map<String, Object>> getUserMapList(Map filterConditions) {
        StringBuffer sql = new StringBuffer();
        sql.setLength(0);
        sql.append(" select * from test_user where 1 = 1 ");
        if ( !filterConditions.isEmpty() ){
            if (StringTool.isNotEmpty(filterConditions.get("name"))){
                sql.append(" and name like '%" + filterConditions.get("name") + "%' ");
            }
            if (StringTool.isNotEmpty(filterConditions.get("gender"))){
                sql.append(" and gender = " + filterConditions.get("gender"));
            }
            if (StringTool.isNotEmpty(filterConditions.get("age"))){
                sql.append(" and age = " + filterConditions.get("age"));
            }
        }
        return this.sqlUtil.queryForList(sql.toString());
    }
}
