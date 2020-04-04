import com.ajman.common.ServerResponse;
import com.ajman.service.ICartService;
import com.ajman.vo.CartVo;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(transactionManager = "transactionManager")
@Rollback(value = false)

@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public abstract class BaseTest {
//public abstract class TestBase extends AbstractTransactionalJUnit4SpringContextTests {
/*
@Rollback
其中只有一个属性就是boolean型的value，作用没变，值为true表示测试时如果涉及了数据库的操作，那么测试完成后，
该操作会回滚，也就是不会改变数据库内容；值为false则与此相反，
 */
}
