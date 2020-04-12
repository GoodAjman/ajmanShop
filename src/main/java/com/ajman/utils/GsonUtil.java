package com.ajman.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class GsonUtil {
     public static String ObjectToJson(Object object){
         Gson gson=new Gson();
         return gson.toJson(object);
     }

    public static <T>T JsonToObject(String jsonValue,Class<T> T){
        Gson gson=new Gson();
        return gson.fromJson(jsonValue,T);
    }

}
