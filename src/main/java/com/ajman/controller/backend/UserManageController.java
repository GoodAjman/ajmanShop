package com.ajman.controller.backend;

import com.ajman.common.Const;
import com.ajman.common.ServerResponse;
import com.ajman.pojo.User;
import com.ajman.service.IUserService;
import com.ajman.utils.CookieUtil;
import com.ajman.utils.GsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage/user")
public class UserManageController {
    @Autowired
    private IUserService userService;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "/login.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session, HttpServletResponse httpServletResponse) {

        ServerResponse<User> response = userService.login(username, password);
        if(response.isSuccess()){
            User user=response.getData();
            if(user.getRole()== Const.Role.ROLE_ADMIN){
                //说明登录的是管理员
//                session.setAttribute(Const.CURRENT_USER,user);
                CookieUtil.writeLoginToken(httpServletResponse, session.getId());
                System.out.println(session.getId());
//            RedisShardedPoolUtil.setEx(session.getId(), JsonUtil.obj2String(response.getData()),Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
                redisTemplate.opsForValue().set(session.getId(), GsonUtil.ObjectToJson(response.getData()));
                return response;
            }else{
                return ServerResponse.createByErrorMessage("不是管理员，无法登录");
            }
        }
        return response;
    }
}
