package com.ajman.service;

import com.ajman.common.ServerResponse;
import com.ajman.vo.OrderVo;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface IOrderService {
    ServerResponse pay(Long orderNo, Integer userId, String path);
    ServerResponse aliCallBack(Map<String,String> params);
    ServerResponse queryOrderPayStatus(Integer userId,Long orderNo);
    ServerResponse<String> cancel(Integer userId,Long orderNo);
    ServerResponse createOrder(Integer id, Integer shippingId);

    ServerResponse getOrderCartProduct(Integer id);

    ServerResponse getOrderDetail(Integer id, Long orderNo);

    ServerResponse getOrderList(Integer id, int pageNum, int pageSize);

    ServerResponse<PageInfo> manageList(int pageNum, int pageSize);

    ServerResponse<OrderVo> manageDetail(Long orderNo);

    ServerResponse<PageInfo> manageSearch(Long orderNo, int pageNum, int pageSize);

    ServerResponse<String> manageSendGoods(Long orderNo);
}
