package com.ajman.utils;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.internal.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    /**
     * 密钥
     */
    private static  final String SECRET="xxxx";
    /**
     * 默认字段key:exp
     */
    private static final String EXP="exp";
    /**
     * 默认字段key:payload
     */
    private static final String PAYLOAD="payload";

    /**
     * 加密
     * @param object 加密数据
     * @param maxTime 有效期（毫秒数）
     * @param <T>
     * @return
     */
    public static <T> String encode(T object,long maxTime){
        try{
            final JWTSigner signer=new JWTSigner(SECRET);
            final Map<String ,Object> data=new HashMap<>(10);
            ObjectMapper objectMapper=new ObjectMapper();
            String jsonString=objectMapper.writeValueAsString(object);
            data.put(PAYLOAD,jsonString);
            data.put(EXP,System.currentTimeMillis()+maxTime);
            return signer.sign(data);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 数据解密
     * @param jwt 解密数据
     * @param tClass 解密类型
     * @param <T>
     * @return
     */
    public static <T> T decode(String jwt,Class<T> tClass) {
        final JWTVerifier jwtVerifier = new JWTVerifier(SECRET);
        try {
            final Map<String, Object> data = jwtVerifier.verify(jwt);
            //判断数据是否超时或者符合标准
            if (data.containsKey(EXP) && data.containsKey(PAYLOAD)) {
                long exp = (long) data.get(EXP);
                long currentTimeMillis = System.currentTimeMillis();
                if (exp > currentTimeMillis) {
                    String json = (String) data.get(PAYLOAD);
                    ObjectMapper objectMapper = new ObjectMapper();
                    return objectMapper.readValue(json, tClass);
                }
            }
            return null;
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }

    //解密token取出userId
    public static <T> T updateDecode(String jwt,Class<T> tClass){
        final JWTVerifier jwtVerifier = new JWTVerifier(SECRET);
        try {
            final Map<String, Object> data = jwtVerifier.verify(jwt);
            String json = (String) data.get(PAYLOAD);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, tClass);
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }
//    public static void main(String[] args) throws InterruptedException {
////        有效期10秒
////        加密：
////        TestCenterAdministratorsVO test = new TestCenterAdministratorsVO();
////        test.setId(1L);
////        test.setLoginName("sa");
////        String token=encode(test,1000000);
////        System.out.println("TOKEN======="+token);
////        //Thread.sleep(10000);
//////      解密
////        TestCenterAdministratorsVO user=decode(token,TestCenterAdministratorsVO.class);
////        System.out.println(user.getId()+user.getLoginName());
//        //removeDecode("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NDcwODkwNTYxNDgsInBheWxvYWQiOiJ7XCJpZFwiOjEsXCJuYW1lXCI6bnVsbCxcInBob25lTnVtYmVyXCI6bnVsbCxcImxvZ2luTmFtZVwiOlwic2FcIixcInNlcmlhbE51bWJlclwiOm51bGwsXCJ0eXBlXCI6bnVsbCxcInRlc3RDZW50ZXJJZFwiOm51bGwsXCJvcmdhbml6YXRpb25JZFwiOm51bGwsXCJtYWluU2l0ZUFkbWluXCI6ZmFsc2UsXCJhZG1pblwiOmZhbHNlLFwib3JnYW5pemF0aW9uTmFtZVwiOm51bGx9In0.FgYm4wSDhkZukqlRukjwvxQ1BM746AWQfCmGucMP3pc");
//        updateDecode("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NDcwMTgxNTU4OTYsInBheWxvYWQiOiJ7XCJpZFwiOjIwMTAwMDAzMyxcIm5hbWVcIjpcIuW6numTtuaYjFwiLFwicGhvbmVOdW1iZXJcIjpudWxsLFwibG9naW5OYW1lXCI6XCJwYW5neWluY2hhbmdcIixcInNlcmlhbE51bWJlclwiOm51bGwsXCJ0eXBlXCI6XCJNQUlOU0lURUFETUlOXCIsXCJ0ZXN0Q2VudGVySWRcIjo3OSxcIm9yZ2FuaXphdGlvbklkXCI6XCIwMDFcIixcIm1haW5TaXRlQWRtaW5cIjp0cnVlLFwiYWRtaW5cIjpmYWxzZSxcIm9yZ2FuaXphdGlvbk5hbWVcIjpudWxsfSJ9.yrYlU4djPPQyu1mneQgkVgLCEQiJ2pKkyX8EVw0NcY8",TestCenterAdministratorsVO.class);
//
//    }
}