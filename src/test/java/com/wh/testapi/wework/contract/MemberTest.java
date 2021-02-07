package com.wh.testapi.wework.contract;

import com.sun.org.glassfish.gmbal.ParameterNames;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.HashMap;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    static Member member;
    @BeforeEach
    void setUp() {
         member=new Member();
    }

    @Test
    void create(){
        HashMap<String,Object> map=new HashMap();
        map.put("userid","whh"+member.random);
        map.put("name","whh"+member.random);
        map.put("department", Arrays.asList(1,2));
        map.put("mobile", "151"+member.random.substring(0,8));
        map.put("email",member.random.substring(0,8)+"@qq.com");
        member.creat(map).then().body("errcode",equalTo(0));

    }
    //89节课20分钟  @ValueSource(strings = {"huoz","wan","heng"})
    //90节课1小时
    @ParameterizedTest
    @CsvFileSource(resources = "/data/member.csv")
//    @ValueSource(strings = {"huoz","wan","heng"})
    void create2(String name,String alias){
        String nameNew=name+member.random;
        String random=String.valueOf(System.currentTimeMillis()).substring(5+0,5+8);
        HashMap<String,Object> map=new HashMap();
        map.put("userid",nameNew);
        map.put("name",nameNew);
        map.put("alias",alias);
        map.put("department", Arrays.asList(1,2));
        map.put("mobile", "183"+random);
        map.put("email",random+"@qq.com");
        member.creat(map).then().body("errcode",equalTo(0));
    }


}