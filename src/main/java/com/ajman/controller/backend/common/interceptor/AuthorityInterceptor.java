package com.ajman.controller.backend.common.interceptor;

import com.ajman.common.Const;
import com.ajman.common.ServerResponse;
import com.ajman.pojo.User;
import com.ajman.utils.CookieUtil;
import com.ajman.utils.GsonUtil;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

@Slf4j
public class AuthorityInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse response, Object handler) throws Exception {
        // 处理之前
        log.info("preHandler");
        //请求中Controller中的方法名
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        //解析HandlerMethod
        String methodName = handlerMethod.getMethod().getName();
        String className = handlerMethod.getBean().getClass().getSimpleName();
        //解析参数
        StringBuilder requestParamBuffer = new StringBuilder();
        Map paramMap = httpServletRequest.getParameterMap();
        Iterator it = paramMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String mapKey = (String) entry.getKey();
            String mapValue =StringUtils.EMPTY;
            //request这个参数的map,里面的value返回的是一个String[]
            Object obj = entry.getValue();
            if (obj instanceof String[]) {
                String[] strs = (String[]) obj;
                mapValue = Arrays.toString(strs);
            }
            requestParamBuffer.append(mapKey).append("=").append(mapValue);

        }
        if (StringUtils.equals(className, "UserManagerController") && StringUtils.equals(methodName, "login")) {
            log.info("权限拦截器拦截到请求,className:{},methodName:{}", className, methodName);
            //如果是拦截到登录请求，不打印参数，因为参数里面有密码，全部会打印到日志中，防止日志泄露
            return true;
        }

        log.info("权限拦截器拦截到请求,className:{},methodName:{},param:{}",className,methodName,requestParamBuffer.toString());
        User user = null;
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if (StringUtils.isNotEmpty(loginToken)) {
            String userJsonStr = (String) redisTemplate.opsForValue().get(loginToken);
            user = GsonUtil.JsonToObject(userJsonStr, User.class);
        }
        if (user == null || (user.getRole().intValue() != Const.Role.ROLE_ADMIN)) {
            //返回false,即不会调用Controller里的方法
            response.reset();//geelynote 这里要添加reset，否则报异常 getWriter() has already been called for this response.
            response.setCharacterEncoding("UTF-8");//geelynote 这里要设置编码，否则会乱码
            response.setContentType("application/json;charset=UTF-8");//geelynote 这里要设置返回值的类型，因为全部是json接口。

            PrintWriter out = response.getWriter();

            //上传由于富文本的控件要求，要特殊处理返回值，这里面区分是否登录以及是否有权限
            if (user == null) {
                if (StringUtils.equals(className, "ProductManageController") && StringUtils.equals(methodName, "richtext_img_upload")) {
                    Map resultMap = Maps.newHashMap();
                    resultMap.put("success", false);
                    resultMap.put("msg", "请登录管理员");
                    out.print(GsonUtil.ObjectToJson(resultMap));
                } else {
                    out.print(GsonUtil.ObjectToJson(ServerResponse.createByErrorMessage("拦截器拦截,用户未登录")));
                }
            } else {
                if (StringUtils.equals(className, "ProductManageController") && StringUtils.equals(methodName, "richtext_img_upload")) {
                    Map resultMap = Maps.newHashMap();
                    resultMap.put("success", false);
                    resultMap.put("msg", "无权限操作");
                    out.print(GsonUtil.ObjectToJson(resultMap));
                } else {
                    out.print(GsonUtil.ObjectToJson(ServerResponse.createByErrorMessage("拦截器拦截,用户无权限操作")));
                }
            }
            out.flush();
            out.close();//geelynote 这里要关闭
            return false;
        }
            return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        log.info("postHandle");
        //处理之后
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        //全部完成
        log.info("afterCompletion");
    }
}
