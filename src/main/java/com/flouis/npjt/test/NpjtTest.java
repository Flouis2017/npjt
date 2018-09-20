package com.flouis.npjt.test;

import com.flouis.npjt.pojo.User;
import com.flouis.npjt.pojo.rowMapper.UserRowMapper;
import com.flouis.npjt.utils.SqlUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
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
    public void testInsert(){
        System.out.println("Test insert operation");
        User user = new User();
        user.setName("Haruaki");
        StringBuffer sql = new StringBuffer();
        sql.setLength(0);
        sql.append(" insert into test_user values(:id, :name, :gender, :age, :homeAddress, :createTime, :updateTime) ");
        int cnt = this.sqlUtil.insert(sql.toString(), user);
        if (cnt > 0){
            System.out.println("插入成功！");
        } else {
            System.out.println("插入失败！");
        }
    }

    @Test
    public void testUpdate(){
        System.out.println("Test update operation");
        StringBuffer sql = new StringBuffer();
        sql.setLength(0);
        sql.append(" update test_user set gender = :gender, age = :age, home_address = :homeAddress, ")
           .append(" create_time = :createTime, update_time = :updateTime where id = :id ");

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", 3);
        paramMap.put("gender", 1);
        paramMap.put("age", 24);
        paramMap.put("homeAddress", "日本东京荒川区町屋4-6-1");
        paramMap.put("createTime", "2018-09-19 20:11:37");
        paramMap.put("updateTime", "2018-09-19 20:11:37");
        int cnt = this.sqlUtil.update(sql.toString(), paramMap);
        if (cnt > 0){
            System.out.println("更新成功！");
        } else {
            System.out.println("更新失败！");
        }
    }

    @Test
    public void testUpdateByPojo(){
        System.out.println("Test update by pojo");
        Integer id = 3;
        StringBuffer sql = new StringBuffer();
        sql.setLength(0);
        sql.append(" select * from test_user where id = :id ");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        // get an pojo by id:
        User user = this.sqlUtil.queryForObject(sql.toString(), paramMap, User.class);
        user.setGender(1);
        user.setAge(22);
        sql.setLength(0);
        sql.append(" update test_user set gender = :gender, age = :age where id = :id ");
        int cnt = this.sqlUtil.updateByPojo(sql.toString(), user);
        if (cnt > 0){
            System.out.println("更新成功！");
        } else {
            System.out.println("更新失败！");
        }
    }

    @Test
    public void testDelete(){
        System.out.println("Test delete operation");
        StringBuffer sql = new StringBuffer();
        sql.setLength(0);
        sql.append(" delete from test_user where name like '%test%' ");
        int cnt = this.sqlUtil.update(sql.toString());
        System.out.println("删除 " + cnt + " 条记录");
    }


    @Test
    public void testQueryForObject(){
        System.out.println("Test queryForObject");
        StringBuffer sql = new StringBuffer();
        sql.setLength(0);
        sql.append(" select * from test_user where id = :id ");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", 1);
        User user = this.sqlUtil.queryForObject(sql.toString(), paramMap, User.class);
        System.out.println(user);
//        System.out.println("createTime: " + user.getCreateTime() + "; Is null: " + (user.getCreateTime()==null) );
    }

    @Test
    public void testQueryForObjectWithoutParamMap(){
        System.out.println("Test queryForObject without paramMap");
        StringBuffer sql = new StringBuffer();
        sql.setLength(0);
        sql.append(" select * from test_user where id = 123 ");
        User user = this.sqlUtil.queryForObject(sql.toString(), User.class);
        System.out.println(user);
//        System.out.println("createTime: " + user.getCreateTime() + "; Is null: " + (user.getCreateTime()==null) );
    }


    @Test
    public void testQueryForMap(){
        System.out.println("Test queryForMap");
        StringBuffer sql = new StringBuffer();
        sql.setLength(0);
        sql.append(" select * from test_user where id = :id ");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", 100);
        Map<String, Object> resultMap = this.sqlUtil.queryForMap(sql.toString(), paramMap);
        System.out.println(resultMap);
    }

    @Test
    public void testQueryForMapWithoutParamMap(){
        System.out.println("Test queryForMap without paramMap");
        Integer id = 11;
        StringBuffer sql = new StringBuffer();
        sql.setLength(0);
        sql.append(" select * from test_user where id = ").append(id);
        Map<String, Object> resultMap = this.sqlUtil.queryForMap(sql.toString());
        System.out.println(resultMap);
    }

    @Test
    public void testQueryForObjectList(){
        System.out.println("Test query object list  WITH PARAM OF RowMapper");
        StringBuffer sql = new StringBuffer();
        sql.setLength(0);
        sql.append(" select * from test_user ");
        Map<String, Object> paramMap = new HashMap<>();

        // Lambda writing style:
        /*List<User> userList = this.sqlUtil.queryForList(sql.toString(), paramMap, (resultSet, rowNum) -> {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setAge(resultSet.getInt("age"));
            user.setGender(resultSet.getInt("gender"));
            user.setHomeAddress(resultSet.getString("home_address"));
            user.setCreateTime(resultSet.getString("create_time"));
            user.setUpdateTime(resultSet.getString("update_time"));
            return user;
        });*/

        List<User> userList = this.sqlUtil.queryForObjectList(sql.toString(), paramMap, new RowMapper<User>() {
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setAge(resultSet.getInt("age"));
                user.setGender(resultSet.getInt("gender"));
                user.setHomeAddress(resultSet.getString("home_address"));
                user.setCreateTime(resultSet.getString("create_time"));
                user.setUpdateTime(resultSet.getString("update_time"));
                return user;
            }
        });
        System.out.println(userList);
    }


    @Test
    public void testQueryForObjectList2(){
        System.out.println("Test query object list WITH PARAM OF ClassType(Class name)");
        StringBuffer sql = new StringBuffer();
        sql.setLength(0);
        sql.append(" select * from test_user where age < 20 ");
        List<User> userList = this.sqlUtil.queryForObjectList(sql.toString(), new HashMap<>(), User.class);
//        System.out.println(User.class);
        System.out.println(userList);
    }


    @Test
    public void testQueryForObjectList3(){
        System.out.println("Test query object list WITH PARAM OF CUSTOMIZED Mapper Object");
        StringBuffer sql = new StringBuffer();
        sql.setLength(0);
        sql.append(" select * from test_user ");
        List<User> userList = this.sqlUtil.queryForObjectList(sql.toString(), new HashMap<>(), new UserRowMapper());
        System.out.println(userList);
    }

    @Test
    public void testQueryForList(){
        System.out.println("Test query for map list");
        StringBuffer sql = new StringBuffer();
        sql.setLength(0);
        sql.append(" select * from test_user ");
        List<Map<String, Object>> list = this.sqlUtil.queryForList(sql.toString());
        System.out.println(list);
    }

}
