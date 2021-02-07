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
        department.list("1").then().statusCode(200);
    }

    @Test
    void create() {
        department.creat("wh_112ggg" + random2, 1).then().body("errcode", equalTo(0));
    }

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

    @ParameterizedTest
    @CsvFileSource(resources = "/data/creatWithDup.csv")
    void createWithDup(String name, Integer expectcode) {
        department.creat(name + random2, 1).then().body("errcode", equalTo(0));
        department.creat(name + random2, 1).then().body("errcode", equalTo(expectcode));
    }

    @Test
    void delete() {
        department.delete("6").then().body("errcode", equalTo(0));
    }

    @Test
    void update() {
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
