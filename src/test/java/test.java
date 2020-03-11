import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {

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


}
