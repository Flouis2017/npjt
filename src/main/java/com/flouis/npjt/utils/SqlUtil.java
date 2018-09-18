package com.flouis.npjt.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Flouis
 * @date 2018/09/18
 * @description package NamedParameterJdbcTemplate CRUD operations
 **/

@Component
public class SqlUtil {

    @Autowired
    private NamedParameterJdbcTemplate npjt;


    /**
     * @description return pojo object mapped to table in DB
     */
    public <T> T queryForObject(String sql, Map<String, ?> paramMap, Class<T> requiredType){
        return this.npjt.queryForObject(sql, paramMap, new BeanPropertyRowMapper<T>(requiredType));
    }

    /**
     * @description query for one row , return a row map
     */
    public Map<String, Object> queryForMap(String sql, Map<String, ?> paramMap){
        return this.npjt.queryForMap(sql, paramMap);
    }

    /**
     * @description without parameter map to query one row map
     */
    public Map<String, Object> queryForMap(String sql){
        return this.queryForMap(sql, new HashMap<String, Object>());
    }

    /**
     * @description return a object list
     */
    public <T> List<T> query(String sql, Map<String, ?> paramMap, RowMapper<T> rowMapper){
        return this.npjt.query(sql, paramMap, rowMapper);
    }

    /**
     * @description return a List<Map>
     */
    public List<Map<String, Object>> queryForList(String sql, Map<String, ?> paramMap){
        return this.npjt.queryForList(sql, paramMap);
    }

    /**
     * @description return a List<Map> without paramMap
     */
    public List<Map<String, Object>> queryForList(String sql){
        return this.queryForList(sql, new HashMap<String, Object>());
    }

    /**
     * @description update operation
     */
    public int update(String sql, Map<String, ?> paramMap){
        return this.npjt.update(sql, paramMap);
    }

    /**
     * @description update without paramMap
     */
    public int update(String sql){
        return this.update(sql, new HashMap<String, Object>());
    }

}
