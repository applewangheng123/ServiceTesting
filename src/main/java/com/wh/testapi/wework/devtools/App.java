package com.wh.testapi.wework.devtools;

import com.wh.testapi.wework.Api;
import com.wh.testapi.wework.Wework;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


//104节课开始
public class App extends Api {
    @Override
    public RequestSpecification getDefaultRequestSpecification() {
        RequestSpecification requestSpecification=super.getDefaultRequestSpecification();
        requestSpecification.queryParam("access_token", Wework.getToken()).contentType(ContentType.JSON);

        requestSpecification.filter((req,res,ctx)->{
            return ctx.next(req,res);
        });
        return requestSpecification;
    }

    public Response listApp(){
        return null;
       // return getResponseFromHar("/api/app.har.json",".*lang=zh_CN.*",null);

    }


}
