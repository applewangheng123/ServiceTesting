package com.wh.testapi.wework.contract;

import com.wh.testapi.wework.Api;
import com.wh.testapi.wework.Wework;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class Contact extends Api {

    String random=String.valueOf(System.currentTimeMillis());

//    public Contact(){
//        reset();
//    }

    //.queryParam要明确写，不然报错
   // public static  void  reset(){

//        requestSpecification
//                .log().all()
//                .queryParam("access_token",Wework.getToken())
//                .contentType(ContentType.JSON)
//                .expect().log().all().statusCode(200);

//        requestSpecification=given();
//        requestSpecification=given()
//                .log().all()
//                .queryParam("access_token",Wework.getToken())
//                .contentType(ContentType.JSON);
//
//        //加解密90节课 1小时20分钟  todo 对请求响应封装
//        requestSpecification.filter((req,res,ctx)->{
//            return ctx.next(req,res);
//        });

   // }

    @Override
    public  RequestSpecification getDefaultRequestSpecification(){
        RequestSpecification requestSpecification=super.getDefaultRequestSpecification();
        requestSpecification.queryParam("access_token",Wework.getToken()).contentType(ContentType.JSON);

        requestSpecification.filter((req,res,ctx)->{
            return ctx.next(req,res);
        });

        return requestSpecification;
  }

}
