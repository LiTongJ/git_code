import com.tong.domain.Account;
import com.tong.service.IAccountService;
import config.SpringConfiguration;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
public class Test {

    @Autowired
    private IAccountService accountService;

    @org.junit.Test
    public void testFindAll() {
        List<Account> accounts = accountService.findAll();
        for (Account account:accounts
             ) {
            System.out.println(account);
        }
    }

    @org.junit.Test
    public void testFindAccoundById() {
        System.out.println(accountService.findAccoundById(2));
    }
}
