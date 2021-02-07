package com.wh.testapi.wework;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import de.sstoehr.harreader.HarReader;
import de.sstoehr.harreader.HarReaderException;
import de.sstoehr.harreader.model.Har;
import de.sstoehr.harreader.model.HarEntry;
import de.sstoehr.harreader.model.HarRequest;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;

public class Api {
    HashMap<String,Object> query=new HashMap<String, Object>();
   // public static RequestSpecification requestSpecification=given();
    public Api(){
        useRelaxedHTTPSValidation();
    }

    public RequestSpecification getDefaultRequestSpecification(){
        return  given().log().all();
    }



    //88节课，55分钟,封装map的封装
    public static String template(String path,HashMap<String,Object>map){
        DocumentContext documentContext= JsonPath.parse(Api.class.getResourceAsStream(path));
        map.entrySet().forEach(entry->{
            documentContext.set(entry.getKey(),entry.getValue());
        });

        return documentContext.jsonString();
    }

    //104节课6分钟
    public   Response getResponseFromHar(String path,String patten,HashMap<String,Object>map){
        //103节课 40分钟
        Restful restful=getApiFromHar(path,patten);
        restful=updateApiFromMap(restful,map);
        return getResponseFromRestful(restful);
    }


    //103节课 3分钟
    public   Response templateFromHar(String path,String patten,HashMap<String,Object>map){
        //103节课 3分钟  读数据有2中，1.jsonparse  2.封装好的har文件https://github.com/SmartBear/har-java
        HarReader harReader = new HarReader();
        try {
            Har har = harReader.readFromFile( new File(getClass().getResource("/Api/app.har.json").getPath()) );
            HarRequest request = new HarRequest();
            for(HarEntry entry:har.getLog().getEntries()){
                request=entry.getRequest();
                if( request.getUrl().matches(patten)){
                    break;
                }
            }

            //103节课，28分钟
            Restful restful=new Restful();
            restful.method=request.getMethod().name().toLowerCase();
            //todo去掉url中的querey部分
            restful.url=request.getUrl();
            request.getQueryString().forEach(q->{
                restful.query.put(q.getName(),q.getValue());
            });

            restful.body=request.getPostData().getText();



        } catch (HarReaderException e) {
            e.printStackTrace();
        }

        return null;
    }



    //90节课19分钟  todo  伪代码，还未实现
    //1.读取har请求 2.根据map进行更新 3.
    public   Response templateForHar(String path,String patten,HashMap<String,Object>map){

        DocumentContext documentContext= JsonPath.parse(Api.class.getResourceAsStream(path));
        map.entrySet().forEach(entry->{
            documentContext.set(entry.getKey(),entry.getValue());
        });
        //读取har文件的method和url
        String method=documentContext.read("method");
        String url=documentContext.read("url");

        return  getDefaultRequestSpecification().when().request(method,url);



    }

    //90节课35分钟  todo  支持从swagger文件读取
    //1.读取har请求 2.根据map进行更新
    //todo 分析swagger dodegen
    //todo 支持从swagger自动生成接口定义并发送
    public  Response templateForSwagger(String path,String patten,HashMap<String,Object>map){

        DocumentContext documentContext= JsonPath.parse(Api.class.getResourceAsStream(path));
        map.entrySet().forEach(entry->{
            documentContext.set(entry.getKey(),entry.getValue());
        });
        //读取har文件的method和url
        String method=documentContext.read("method");
        String url=documentContext.read("url");

        return  getDefaultRequestSpecification().when().request(method,url);

    }

    public Restful getApiFromHar(String path,String patten){
        //103节课 38分钟  读数据有2中，1.jsonparse  2.封装好的har文件https://github.com/SmartBear/har-java
        HarReader harReader = new HarReader();
        try {
            Har har = harReader.readFromFile( new File(getClass().getResource(path).getPath()) );
            HarRequest request = new HarRequest();
            Boolean match=false;
            for(HarEntry entry:har.getLog().getEntries()){
                request=entry.getRequest();
                if( request.getUrl().matches(patten)){
                    match=true;
                    break;
                }
            }
            if(match==false){
                request=null;
                throw new Exception("测试用的异常");
            }

            //103节课，28分钟
            Restful restful=new Restful();
            restful.method=request.getMethod().name().toLowerCase();
            //todo去掉url中的querey部分
            restful.url=request.getUrl();
            request.getQueryString().forEach(q->{
                restful.query.put(q.getName(),q.getValue());
            });

            restful.body=request.getPostData().getText();
            return restful;



        } catch (HarReaderException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }

    }

    public Restful getApiFromYaml(String path){

        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        try {
           return   objectMapper.readValue(WeworkConfig.class.getResourceAsStream(path), Restful.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    //103节课30分钟
    public Restful updateApiFromMap(Restful restful,HashMap<String, Object> map){
        if(map==null){
            return restful;
        }

        //获取map的值，并赋值
        if (restful.method.toLowerCase().contains("get")) {
            map.entrySet().forEach(entry -> {
                restful.query.replace(entry.getKey(), entry.getValue().toString());
            });
        }
        //post方法
        if (restful.method.toLowerCase().contains("post")) {
            if(map.containsKey("_body")){
                restful.body=map.get("_body").toString();

            }
            if(map.containsKey("_file")){
                String filePath=map.get("_file").toString();
                map.remove("_file");
                restful.body=template(filePath,map);
            }

        }
        return restful;

    }

    public Response getResponseFromRestful(Restful restful){
        RequestSpecification requestSpecification=getDefaultRequestSpecification();
        //读取query的值,并获取赋值
        if(restful.query!=null){
            restful.query.entrySet().forEach(entry -> {
                requestSpecification.queryParam(entry.getKey(), entry.getValue());
            });
        }

        if(restful.body!=null){
            requestSpecification.body(restful.body);
        }

        //todo 104节课18分钟  多环境支持，替换url，更新host的header  最推荐ngix反向代理
        String []url=updateUrl(restful.url);

        return requestSpecification.log().all()
                .header("Host",url[0])
                .when().request(restful.method, url[1])
                .then().log().all().extract().response();

    }

    //多环境支持，替换url  主机名保持现状，ip地址要变
    public String[] updateUrl(String url){
        HashMap<String,String>hosts=WeworkConfig.getInstance().env.get(WeworkConfig.getInstance().current);

        String host="";
        String urlNew="";
        for(Map.Entry<String,String>entry : hosts.entrySet()){
            if(url.contains(entry.getKey())){
                host=entry.getKey();
                urlNew=url.replace(entry.getKey(),entry.getValue());
            }

        }

        return new String[]{host,urlNew};
    }


    

    //90节课35分钟  todo  支持从yaml文件  Done
    public  Response getResponseFromYaml(String path, HashMap<String, Object> map)  {


        Restful restful=getApiFromYaml(path);
        restful=updateApiFromMap(restful,map);

        RequestSpecification requestSpecification=getDefaultRequestSpecification();
        //读取query的值,并获取赋值
        if(restful.query!=null){
            restful.query.entrySet().forEach(entry -> {
                requestSpecification.queryParam(entry.getKey(), entry.getValue());
            });
        }

                //todo 多环境支持，替换url，更新host的heard
        return requestSpecification.log().all()
                .when().request(restful.method, restful.url)
                .then().log().all().extract().response();


    }



    //90 todo  支持从wsdl  soap文件
    //动态调用
    public  Response readApiFromYaml(String path,HashMap<String,Object>map){
        return null;
    }
}


