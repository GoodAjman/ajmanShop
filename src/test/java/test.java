import com.ajman.common.ServerResponse;
import com.ajman.pojo.User;
import com.ajman.service.ICartService;
import com.ajman.service.IOrderService;
import com.ajman.service.IUserService;
import com.ajman.vo.CartVo;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test extends BaseTest {

    @Autowired
    private ICartService cartService;

    @Autowired
    private IOrderService orderService;
    @Autowired
    private IUserService userService;
    @Test
    public void main() {

        String a = "123234343";
        String regEx = "^[0-9]*";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(a);
//     System.out.println(m.replaceAll("-"));
        while (m.find()) {
            System.out.println(m.group());
        }
        //打印结果 ：  --20-

    }

    @Test
    public void testCart() {
        Integer userId = 1;
        String productId = "26";
        Integer count = 1;
//     ServerResponse<CartVo> add = cartService.deleteProduct(userId, productId);
        ServerResponse<Integer> cartProductCount = cartService.getCartProductCount(userId);
        System.out.println(cartProductCount.toString());
    }

    @Test
    public void testMap() {
//     Map<String,String[]> map= Maps.newHashMap();
//     map.put("1",new String[]{"a"});
//     map.put("2",new String[]{"b"});
//     map.put("3",new String[]{"c"});
//     System.out.println(map.get("1")[0]);
//     System.out.println(map.get("e"));


    }

    @Test
    public void test(){
        cartService.update(21,26,5);
        ServerResponse<CartVo> list = cartService.list(21);
//        list.getData().getCartProductVoList().stream().forEach(item -> System.out.println("huanghuhu"+item));
        ServerResponse<User> information = userService.getInformation(13);
        System.out.println("1234567"+information.getData().getUpdateTime());

    }
}
