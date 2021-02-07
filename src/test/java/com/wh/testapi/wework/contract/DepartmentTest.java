package com.wh.testapi.wework.contract;

import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.util.HashMap;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class DepartmentTest {

    Department department;
    String random2 = String.valueOf(System.currentTimeMillis());

    @BeforeEach
    void setUp() {
        if (department == null) {
            department = new Department();
        }
    }

    @Test
    void list() {
        // department.list("").then().statusCode(200).body("department.name[0]",equalTo("杭州wh公司"));;
        // department.list("1").then().statusCode(200).body("department.name[0]",equalTo("杭州wh公司"));;
        department.list("1").then().statusCode(200);
    }

    @Test
    void create() {
//        department.creat("wh_12",1);
        // department.creat("wh_112",1).then().body("errcode",equalTo(60008));
        //第二部，增加ramdom
        department.creat("wh_112ggg" + random2, 1).then().body("errcode", equalTo(0));
    }

    //88节课43分钟  更推荐这个，可以相传多少就传多少
    @Test
    void createByMap() {
        HashMap<String, Object> map = new HashMap<String, Object>() {{
            put("name", "whh" + random2);
            put("parentid", 1);
        }};
        department.creat(map).then().body("errcode", equalTo(0));
    }

    @Test
    void createWithChinese() {
        department.creat("wh_11" + random2, 1).then().body("errcode", equalTo(0));
    }

    //90节课，1小时18分钟
    @ParameterizedTest
    @CsvFileSource(resources = "/data/creatWithDup.csv")
    void createWithDup(String name, Integer expectcode) {
        department.creat(name + random2, 1).then().body("errcode", equalTo(0));
        department.creat(name + random2, 1).then().body("errcode", equalTo(expectcode));
    }

    @Test
    void delete() {
        //可以和update一样，先创建一个，然后删除
//        String s="杭州wh公司"+random;
//        department.creat(s,1);
//        Integer idInt=department.list("").path("department.find{ it.name=='"+s+"'}.id");
//        System.out.println("我的天哪"+idInt);
//        //String id=String.valueOf(idInt);
//        department.delete(idInt).then().body("errcode",equalTo(0));
        department.delete("6").then().body("errcode", equalTo(0));
    }

    @Test
    void update() {
        //department.update("3","wjupdatex");
        //第二种方法
        //String s="杭州wh公司"+random;
        //department.creat(s,1);
//        Integer idInt=department.list("").path("department.find{ it.name=='"+s+"'}.id");
//        String id=String.valueOf(idInt);
        //department.update(id,"wjupdatex"+random).then().body("errcode",equalTo(0));
        department.update("6", "wjupdatex22").then().body("errcode", equalTo(0));
    }

    //90节课40分钟  todo
    void updateAllx() {
        HashMap<String, Object> map = new HashMap<>();
        // department.updateAll(map).then().statusCode(200);
    }

    @Test
    void deleteAll() {
        department.deleteAll();

    }
}
