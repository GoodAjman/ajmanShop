import com.ajman.common.ServerResponse;
import com.ajman.service.ICartService;
import com.ajman.vo.CartVo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test extends BaseTest {

    @Autowired
    private ICartService cartService;
 @Test
 public  void main(){

     String a = "123234343";
     String regEx = "^[0-9]*";
     Pattern p = Pattern.compile(regEx);
     Matcher m = p.matcher(a);
//     System.out.println(m.replaceAll("-"));
while(m.find()){
    System.out.println(m.group());
}
     //打印结果 ：  --20-

 }

 @Test
    public void testCart(){
     Integer userId=1;
     String productId="26";
     Integer count=1;
//     ServerResponse<CartVo> add = cartService.deleteProduct(userId, productId);
     ServerResponse<Integer> cartProductCount =cartService.getCartProductCount(userId);
     System.out.println(cartProductCount.toString());
 }

}
