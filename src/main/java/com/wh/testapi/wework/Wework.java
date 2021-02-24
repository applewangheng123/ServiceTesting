package com.wh.testapi.wework;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

public class Wework {

    private static String token;
    @Test
    public static  String getWeworkToken(String secret) {

        return RestAssured.given().queryParam("corpid", WeworkConfig.getInstance().corpId)
                .queryParam("corpsecret", secret)
                .when().get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                .then().log().all().statusCode(200)
                .extract().path("access_token");
    }



    //使用单例存储token?
    public static String  getToken() {
        //todo:支持多种类型的token
        if (token == null) {
            token = getWeworkToken(WeworkConfig.getInstance().contactSecret);
        }
        return  token;
    }

}
