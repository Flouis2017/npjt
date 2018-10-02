package com.flouis.npjt.dao.impl;

import com.flouis.npjt.dao.FileOperationDao;
import com.flouis.npjt.utils.SimpleSqlUtil;
import com.flouis.npjt.utils.SqlUtil;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Flouis
 * @date 2018/10/02
 * @description TODO
 **/

@Repository
public class FileOperationDaoImpl implements FileOperationDao {

    @Autowired
    private SimpleSqlUtil simpleSqlUtil;

    @Autowired
    private SqlUtil sqlUtil;

    public String getKeyInsert(Object[] args){
        StringBuffer sql =  new StringBuffer();
        sql.setLength(0);
        sql.append(" insert into file(url, name, original_name, create_time, update_time) ")
           .append(" values(?, ?, ?, now(), now()) ");
        return this.simpleSqlUtil.getKeyUpdate(sql.toString(), args);
    }


    public List<Map<String, Object>> getFileList(){
        StringBuffer sql = new StringBuffer();
        sql.setLength(0);
        sql.append(" select * from file ");
        return this.sqlUtil.queryForList(sql.toString());
    }

    public int deleteById(String id){
        StringBuffer sql = new StringBuffer();
        sql.setLength(0);
        sql.append(" delete from file where id = :id ");
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("id", id);
        return this.sqlUtil.update(sql.toString(), paramMap);
    }

}
