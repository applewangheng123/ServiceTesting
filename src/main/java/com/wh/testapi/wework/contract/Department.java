package com.wh.testapi.wework.contract;

import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;


public class Department extends Contact {

    //2021年2月24
    //获取部门列表 get请求
    public    Response list(String id){

        HashMap<String,Object>map=new HashMap<>();
        map.put("id",id);
        Response response=getResponseFromYaml("/api/list.yaml",map);
        //reset();
        return response;

    }


    //创建部门   传参：1.模板化-创建一个模板，往里面传参    2.模型化-为post创建一个对象
    //使用jsonpath 读写json（restassued封装的不能写）https://github.com/json-path/JsonPath
    public Response creat(String name, int parentId) {

        //101节课41分钟
        HashMap<String, Object> map = new HashMap<>();
        map.put("_file", "/data/create.json");
        map.put("$.name", name);
        map.put("$.parentid", parentId);
        return getResponseFromYaml("/api/create.yaml", map);
    }


    //88节课39分钟,使用这种方式,create.json的数据可以改成null,传多个参数
    public Response creat(HashMap<String, Object> map) {
        //101节课58分钟
        map.put("_file", "/data/create.json");
        return getResponseFromYaml("/api/create.yaml",map);
    }


    //删除部门
    public Response delete(String id) {
        //102节课 5分钟
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        return getResponseFromYaml("/api/delete.yaml",map);

    }

    //更新部门
    public Response update(String id, String name) {


        //102节课4分钟
        HashMap<String, Object> map = new HashMap<>();
        map.put("_file", "/data/update.json");
        map.put("$.name", name);
        map.put("$.id", id);
        return getResponseFromYaml("/api/update.yaml",map);

    }


    //90节课17分钟  todo
    public Response update(HashMap<String,Object>map){
        return  templateForHar("demo.har.json","",map);
    }


    //88节课 35分钟  todo java.lang.Integer cannot be cast to java.lang.String
    public Response deleteAll() {
        List<String> list = list("").then().extract().path("department.id");
        System.out.println(list);
        list.forEach(id -> delete(String.valueOf(id)));
        return null;

    }

    //90节课 42分钟  todo
    public Response  updateAll(HashMap<String,Object>map){
        return  readApiFromYaml("",map);

    }


}
