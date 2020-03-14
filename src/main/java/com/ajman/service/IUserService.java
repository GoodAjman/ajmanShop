package com.ajman.service;

import com.ajman.common.ServerResponse;
import com.ajman.pojo.User;

/**
 * ~
 *
 * @Author keny
 * @Date 2020/3/12 10:47
 * @Version 1.0
 */

public interface IUserService {
    ServerResponse<User> login(String username, String password);

    ServerResponse<String> register(User user);

    ServerResponse<String> checkValid(String str, String type);

    ServerResponse selectQuestion(String username);

    ServerResponse<String> checkAnswer(String username, String question, String answer);

    ServerResponse<String> forgetPassword(String username, String newPassword, String forgetToken);

    ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user);

    ServerResponse<User> updateInformation(User user);

    ServerResponse<User> getInformation(Integer userId);

    ServerResponse checkAdminRole(User user);

}
