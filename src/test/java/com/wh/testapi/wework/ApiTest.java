package com.wh.testapi.wework;

import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;

import static io.restassured.RestAssured.given;

class ApiTest {

    @Test
    void templateFromYaml() throws IOException {
        Api api=new Api();
        api.getResponseFromYaml("/api/list.yaml",null).then().statusCode(200);

    }

    @Test
    void request() {
        RequestSpecification req= given().log().all();
        req.queryParam("id","1");
        req.queryParam("d","xxx");
        req.get("http://www.baidu.com");
    }

    @Test
    void resource(){
        URL url= getClass().getResource("/Api/app.har.json");
        System.out.println(url.getFile());
        System.out.println(url.getPath());

    }

    @Test
    void getApiFromHar() {
        Api api=new Api();
        System.out.println(api.getApiFromHar("/api/app.har.json",".*lang=zh_CN.*").url);
        //System.out.println(api.getApiFromHar("/api/app.har.json","ttwwww").url);

    }

    //104节课7分钟
    @Test
    void getResponseFromHar() {
        Api api=new Api();
        api.getResponseFromHar("/api/app.har.json",".*lang=zh_CN.*",null);
    }

}