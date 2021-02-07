package com.wh.testapi.wework;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.util.HashMap;

public class WeworkConfig {
    public String agentId="1000003";
    public String secret="N3v8m00XaeO_zn7jVEt6uU337SGBsWSY7989fPJ5dy4";
    //企业ID
    public String corpId="ww45b859895b3a9f7b";
    //通讯录secret
    public String contactSecret="Lsdg-5kJGjRWm_HkhixRKoHWH0zMdHtLnK86MS8pShw";

    //104 20分钟，多环境
    public String current="test";
    public HashMap<String,HashMap<String,String>>env;


    //使用单例维护全局配置
    private static WeworkConfig weworkConfig;
    public  static WeworkConfig getInstance(){
        if(weworkConfig==null){
            //最开始
           // weworkConfig=new WeworkConfig();
            //99节课56分钟
            weworkConfig=load("/conf/WeworkConfig.yaml");
            System.out.println(weworkConfig);
            System.out.println(weworkConfig.agentId);
        }
        return  weworkConfig;

    }


    //从文件中读取
    public static  WeworkConfig load(String path) {
//        //todo  从yalm或者json读取数据
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

            //读取文件
        try {
            return objectMapper.readValue(WeworkConfig.class.getResourceAsStream(path),WeworkConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;

        }

//        try {
//            //读取文件
//            //序列化的手段，把数据存储到一个文件，后面在读
//            // System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(WeworkConfig.getInstance()));
//            System.out.println(objectMapper.writeValueAsString(getInstance()));
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }


    }

}
