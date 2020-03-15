package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * Spring中的新注解
 *  configuration
 *       作用:指定当前类是一个配置类
 *  ComponentScan:需要扫描的包
 *
 *  bean : 把方法的返回值存入ioc
 *      属性: name : bean的id,当不写时,默认当前方法名
 */

@Configuration
@ComponentScan(basePackages = {"com.tong"})
@Import(JdbcConfig.class)
@PropertySource("classpath:db.properties")
public class SpringConfiguration{

}