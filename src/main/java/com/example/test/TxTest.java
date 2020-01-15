package com.example.test;

import com.example.config.TxConfig;
import com.example.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 声明式事务
 *      -1-  环境搭建
 *              导入相关依赖 ：数据源，数据库，spring jdbc
 *              配置数据源，jdbcTemplate操作数据
 *              标注了 @Transactional 注解的方法，表示当前方法是一个事务方法
 *              配置类上 标注  @EnableTransactionManagement  注解  开启事务管理功能
 *              配置事务管理器来控制事务  @Bean
 */
public class TxTest {
    public static void main(String[] args) {

        test01();

    }

    /**  @EnableTransactionManagement  原理
     *
     *    -1-  @Import(TransactionManagementConfigurationSelector.class)
     *          利用  TransactionManagementConfigurationSelector  给容器中导入组件  AdviceMode mode() default AdviceMode.PROXY;
     *          默认导入的两个组件为  AutoProxyRegistrar  和  ProxyTransactionManagementConfiguration
     *    -2-  AutoProxyRegistrar  给容器中注册一个组件  InfrastructureAdvisorAutoProxyCreator
     *              creator 后置处理器利用后置处理器机制在对象创建以后，包装对象返回一个代理对象(增强器)，代理对象执行方法利用拦截器链进行调用
     *    -3-  ProxyTransactionManagementConfiguration
     *              给容器中注册事务增强器，事务增强器要使用事务注解的信息  用 AnnotationTransactionAttributeSource 来解析事务注解
     *              给容器中注册事务拦截器  transactionInterceptor  保存了事务管理器 和 事务属性
     *              transactionInterceptor 是一个 MethodInterceptor，在目标方法执行的时候，执行事务拦截器链
     *              先获取事务的相关属性，在获取事务管理器TransactionManagement  如果没有则从容器中根据类型获取
     *              执行目标方法，若出现异常则调用回滚方法进行事务回滚,否则提交事务
     *
     */
    private static void test01() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TxConfig.class);
        UserService service = context.getBean(UserService.class);
        service.insert();

        context.close();
    }
}
