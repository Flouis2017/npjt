package com.flouis.npjt.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
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
     * And with BeanPropertyRowMapper param , it will automatically turn underline into hump
     */
    public <T> T queryForObject(String sql, Map<String, ?> paramMap, Class<T> requiredType){
        Object obj = null;
        try{
            obj = this.npjt.queryForObject(sql, paramMap, new BeanPropertyRowMapper<>(requiredType));
        } catch (Exception e){
            System.out.println("Exception: " + e.getMessage());
        }
        return (T) obj;
    }

    /**
     * @description similar to the function above differ from without paramMap
     */
    public <T> T queryForObject(String sql, Class<T> requiredType){
        return this.queryForObject(sql, new HashMap<>(), requiredType);
    }

    /**
     * @description query for one row , return a row map
     */
    public Map<String, Object> queryForMap(String sql, Map<String, ?> paramMap){
        Map<String, Object> resultMap = null;
        try{
            resultMap = this.npjt.queryForMap(sql, paramMap);
        } catch (Exception e){
            System.out.println("Exception: " + e.getMessage());
        }
        return resultMap;
    }

    /**
     * @description without parameter map to query one row map
     */
    public Map<String, Object> queryForMap(String sql){
        return this.queryForMap(sql, new HashMap<>());
    }

    /**
     * @description return an Object list
     */
    public <T> List<T> queryForObjectList(String sql, Map<String, ?> paramMap, RowMapper rowMapper){
        return this.npjt.query(sql, paramMap, rowMapper);
    }

    /**
     * @description return an Object list, this function is extended from above cause
     * BeanPropertyRowMapper this class implements interface of RowMapper.
     */
    public <T> List<T> queryForObjectList(String sql, Map<String, ?> paramMap, Class<T> type){
        return this.npjt.query(sql, paramMap, new BeanPropertyRowMapper<>(type));
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
        return this.queryForList(sql, new HashMap<>());
    }


    /**
     * @description update operation including insertion & update & deletion
     */
    public int update(String sql, Map<String, ?> paramMap){
        return this.npjt.update(sql, paramMap);
    }


    /**
     * @description update without paramMap
     */
    public int update(String sql){
        return this.update(sql, new HashMap<>());
    }


    /**
     * @description update by an pojo
     */
    public int updateByPojo(String sql, Object object){
        return this.npjt.update(sql, new BeanPropertySqlParameterSource(object));
    }


    /**
     * @description insert a pojo into table as long as the properties of pojo map to the columns of table
     */
    public <T> int insert(String sql, T object){
        return this.npjt.update(sql, new BeanPropertySqlParameterSource(object));
    }

}
