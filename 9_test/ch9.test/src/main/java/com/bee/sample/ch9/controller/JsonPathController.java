package com.bee.sample.ch9.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.beetl.ext.fn.Json;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author linhx
 * @date 2022-10-24 14:25
 */
public class JsonPathController {

    /**
     * TestJsonPath 各种类型测试
     */
    public static void main(String[] args) throws JSONException, IOException {

        String JsonString = "{\n" +
                "    \"store\": {\n" +
                "        \"book\": [\n" +
                "            {\n" +
                "                \"category\": \"reference\",\n" +
                "                \"author\": \"Nigel Rees\",\n" +
                "                \"title\": \"Sayings of the Century\",\n" +
                "                \"price\": 8.95\n" +
                "            },\n" +
                "            {\n" +
                "                \"category\": \"fiction\",\n" +
                "                \"author\": \"Evelyn Waugh\",\n" +
                "                \"title\": \"Sword of Honour\",\n" +
                "                \"price\": 12.99\n" +
                "            },\n" +
                "            {\n" +
                "                \"category\": \"fiction\",\n" +
                "                \"author\": \"Herman Melville\",\n" +
                "                \"title\": \"Moby Dick\",\n" +
                "                \"isbn\": \"0-553-21311-3\",\n" +
                "                \"price\": 8.99\n" +
                "            },\n" +
                "            {\n" +
                "                \"category\": \"fiction\",\n" +
                "                \"author\": \"J. R. R. Tolkien\",\n" +
                "                \"title\": \"The Lord of the Rings\",\n" +
                "                \"isbn\": \"0-395-19395-8\",\n" +
                "                \"price\": 22.99\n" +
                "            }\n" +
                "        ],\n" +
                "        \"bicycle\": {\n" +
                "            \"color\": \"red\",\n" +
                "            \"price\": 19.95\n" +
                "        }\n" +
                "    },\n" +
                "    \"expensive\": \"10\"\n" +
                "}";

        ObjectMapper objectMapper = new ObjectMapper();
        Map map = objectMapper.readValue(JsonString, Map.class);
        System.out.println(JSON.toJSONString(map));

        // 或者借助 jsonPath
        DocumentContext parse = JsonPath.parse(JsonString);
        String s = parse.jsonString();

        // 输出 所有 book 对象
        String jsonPath = "$.store.book.[*]";
        List msg = JsonPath.read(JsonString, jsonPath);
        System.out.println(msg.toString());
        //[{"category":"reference","author":"Nigel Rees","title":"Sayings of the Century","price":8.95},{"category":"fiction","author":"Evelyn Waugh","title":"Sword of Honour","price":12.99},{"category":"fiction","author":"Herman Melville","title":"Moby Dick","isbn":"0-553-21311-3","price":8.99},{"category":"fiction","author":"J. R. R. Tolkien","title":"The Lord of the Rings","isbn":"0-395-19395-8","price":22.99}]

        //输出 所有 author 字段
        jsonPath = "$.store.book[*].author";
        List readMsg = JsonPath.read(JsonString, jsonPath);
        System.out.println(readMsg.toString());
        //["Nigel Rees","Evelyn Waugh","Herman Melville","J. R. R. Tolkien"]

        //输出的 集合不存在 不会空指针 List = []
        jsonPath = "$.store.book[*].author111";
        List readMsg1 = JsonPath.read(JsonString, jsonPath);
        System.out.println(readMsg1.toString());
        // []

        // 输出 单个字段 接受需要指定类型
        jsonPath = "$.expensive";
        String readMsg2 = JsonPath.read(JsonString, jsonPath);
        System.out.println(readMsg2);
        // 10

        // 取出list 用LinkedHashMap 接收
        String jsonPath4 = "$.store.book";
        List<LinkedHashMap> msg4 = JsonPath.read(JsonString, jsonPath4);
        System.out.println(msg4.toString());

        // List<LinkedHashMap> 转换 jsonArray
        JSONArray myResult = new JSONArray();
        for (LinkedHashMap linkedHashMap : msg4) {
            JSONObject jSONObject = new JSONObject();
            Iterator it = linkedHashMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                jSONObject.put((String) entry.getKey(), entry.getValue());
            }
            myResult.put(jSONObject);
        }
        System.out.println(myResult);


        //修改 某个字段值
        DocumentContext doc = JsonPath.parse(JsonString).set("$.store.book.[2].author", "Jayway");
        System.out.println(doc.read("$.store.book.[1].author").toString());
        System.out.println(doc.read("$.store.book.[2].author").toString());

        //修改全部
        doc = JsonPath.parse(JsonString).set("$.store.book.[*].author", "Jayway");
        System.out.println(doc.read("$.store.book.[*].author").toString());

        //DocumentContext 转string
        String docString = doc.jsonString();
        System.out.println(docString);

    }

}
