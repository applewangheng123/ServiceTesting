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
