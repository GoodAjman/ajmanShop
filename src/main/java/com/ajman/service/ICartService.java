package com.ajman.service;

import com.ajman.common.ServerResponse;
import com.ajman.vo.CartVo;

public interface ICartService {


    ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count);

    ServerResponse<CartVo> update(Integer id, Integer productId, Integer count);

    ServerResponse<CartVo> deleteProduct(Integer id, String productIds);

    ServerResponse<CartVo> list(Integer userId);

    ServerResponse<CartVo> selectOrUnSelect(Integer userId, Integer ProductId,Integer checked);

    ServerResponse<Integer> getCartProductCount(Integer userId);
}
