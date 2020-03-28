package com.ajman.controller.backend;

import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;

@RequestMapping("/huawei")
@Controller
public class BaseController {
    @RequestMapping("/test")
    @ResponseBody
    public String timeTest(@RequestParam("timeStamp") String timeStamp, @RequestParam(value = "expireTime", required = false) @DateTimeFormat(pattern = "yyyyMMddHHmmss") DateTime expireTime, HttpServletRequest request) {
        Map<String, String[]> paramsPar = request.getParameterMap();
        Iterator<String> iterator=paramsPar.keySet().iterator();
        while (iterator.hasNext()){
            String key=iterator.next();
            String[] value=paramsPar.get(key);
            System.out.println(value[0]);
        }
        return "hi";
    }
}
