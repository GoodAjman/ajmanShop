package com.ajman.controller.backend;

import com.ajman.common.Const;
import com.ajman.common.ResponseCode;
import com.ajman.common.ServerResponse;
import com.ajman.pojo.User;
import com.ajman.service.ICategoryService;
import com.ajman.service.IUserService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

@Controller
@RequestMapping("/manage")
public class CategoryManageController {
    @Autowired
    private IUserService userService;

    @Autowired
    private ICategoryService categoryService;
    @RequestMapping("/test")
    @ResponseBody
    public String timeTest(@RequestParam("timeStamp") String timeStamp, @RequestParam(value = "expireTime")  String expireTime, HttpServletRequest request) throws UnsupportedEncodingException {
        Map<String, String[]> paramsPar = request.getParameterMap();
        Iterator<String> iterator=paramsPar.keySet().iterator();
        while (iterator.hasNext()){
            String key=iterator.next();
            String[] value=paramsPar.get(key);
            String decode=  UriUtils.encodePath(value[0], "UTF-8");
            String Udecode=UriUtils.decode(decode,"UTF-8");
//            String decode = UriUtils.encode(value[0], "UTF-8");
            String[] tempValue = new String[]{URLEncoder.encode(value[0], "UTF-8")};
            String[] curValue = new String[]{URLDecoder.decode(value[0], "UTF-8")};
            System.out.println(value[0]);
            System.out.println(tempValue[0]);
            System.out.println(curValue[0]);
            System.out.println(decode);
            System.out.println(Udecode);
        }
        return "hi";
    }
    @RequestMapping(value = "/add_category.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addCategory(HttpSession session, String categoryName, @RequestParam(value = "parentId", defaultValue = "0") int parentId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录");
        }
        //校验是否是管理员
        if (userService.checkAdminRole(user).isSuccess()) {
            //是管理员
            //增加我们分类的逻辑
            return categoryService.addCategory(categoryName, parentId);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }

    @RequestMapping(value = "/set_category_name.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse setCategoryName(HttpSession session, Integer categoryId, String categoryName) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        if (userService.checkAdminRole(user).isSuccess()) {

            return categoryService.updateCategoryName(categoryId, categoryName);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }

    @RequestMapping(value = "/get_category.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(HttpSession session, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录");
        }
        if (userService.checkAdminRole(user).isSuccess()) {
            //查询子节点的category信息,并且不递归,保持平级
            return categoryService.getChildrenParallelCategory(categoryId);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }

    @RequestMapping(value = "/get_deep_category.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpSession session, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录");
        }
        if (userService.checkAdminRole(user).isSuccess()) {
            //查询当前节点的id和递归子节点的id
//            0->10000->100000
            return categoryService.selectCategoryAndChildrenById(categoryId);

        } else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }

    }
}
