package com.flouis.npjt.test;

import com.flouis.npjt.utils.SqlUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Flouis
 * @date 2018/09/19
 * @description test NamedParameterJdbcTemplate
 **/

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class NpjtTest {

    @Autowired
    private SqlUtil sqlUtil;

    @Test
    public void testQueryForMap(){
        StringBuffer sql = new StringBuffer();
        sql.setLength(0);
        sql.append(" select * from test_user where id = :id ");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", 1);
        Map<String, Object> resultMap = this.sqlUtil.queryForMap(sql.toString(), paramMap);
        System.out.println(resultMap.get("name"));
    }

}
