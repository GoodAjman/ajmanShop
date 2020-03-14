package com.ajman.controller.backend;

import com.ajman.common.Const;
import com.ajman.common.ResponseCode;
import com.ajman.common.ServerResponse;
import com.ajman.pojo.Product;
import com.ajman.pojo.User;
import com.ajman.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.function.IntUnaryOperator;

@Controller
@RequestMapping("manage/product")
public class ProductManageController {
    @Autowired
    private IUserService userService;

    public ServerResponse productSave(HttpSession session, Product product){
        User user=(User) session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }
        if(userService.checkAdminRole(user).isSuccess()){
            //增加产品的业务逻辑

        }
    }
}
