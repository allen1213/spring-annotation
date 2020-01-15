package com.example.test;

import com.example.aop.MathCalculator;
import com.example.config.AOPConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * AOP[动态代理]  指在程序运行期间动态的将某段代码切入到指定方法指定位置进行运行的编程方式
 *   -1-  导入依赖
 *   -2-  创建一个业务逻辑类MathCalculator
 *         在业务逻辑运行的时候进行日志打印，方法之前，方法运行结果，异常等
 *
 *   -3-  定义一个日志切面类  LogAspects  切面类里面的方法需要动态感知 MathCalculator.div 运行到哪里然后执行
 *          通知方法：
 *                  @Before 前置通知  logStart  在目标方法运行之前运行
 *                  @After 后置通知  logEnd  无论是否正常结束都会调用该方法
 *                  @AfterReturning 返回通知  logReturn
 *                  @AfterThrowing 异常通知  logException
 *                  @Around 环绕通知   动态代理，手动推进目标方法执行(joinPoint.procced)
 *
 *    -4-  给切面类的目标方法添加通知注解
 *    -5-  将切面类和业务逻辑类都添加到容器中
 *    -6-  告诉spring  哪个类是切面类， 在切面类上添加 @Aspect 注解
 *    -7-  在配置类中添加  @EnableAspectJAutoProxy 注解  开启aspectJ自动代理[开启基于注解的aop模式]
 *          test01
 */
public class AOPTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AOPConfig.class);
        MathCalculator bean = context.getBean(MathCalculator.class);
        bean.div(4, 2);
    }

    /**
     * AOP 原理  ： 看给容器中注册了什么组件，什么时候工作，这个组件的功能是什么
     *
     * @EnableAspectJAutoProxy
     *     -1-  @Import(AspectJAutoProxyRegistrar.class)  给容器中导入AspectJAutoProxyRegistrar
     *          利用 AspectJAutoProxyRegistrar 自定义 给容器中注册bean
     *           internalAutoProxyCreator = AnnotationAwareAspectJAutoProxyCreator
     *           给容器中注册了一个 AnnotationAwareAspectJAutoProxyCreator
     *
     *     -2-  AnnotationAwareAspectJAutoProxyCreator
     *          继承了 AspectJAwareAdvisorAutoProxyCreator -> AbstractAdvisorAutoProxyCreator -> AbstractAutoProxyCreator
     *          implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware
     *
     *          关注后置处理器，在bean初始化完成前后做相应操作，自动装配BeanFactory
     *
     *     -3-  AbstractAutoProxyCreator#setBeanFactory(org.springframework.beans.factory.BeanFactory)
     *            AbstractAutoProxyCreator#postProcessBeforeInstantiation(java.lang.Class, java.lang.String)
     *            AbstractAutoProxyCreator#postProcessAfterInitialization(java.lang.Object, java.lang.String)
     *
     *     -4-  AbstractAdvisorAutoProxyCreator#setBeanFactory(org.springframework.beans.factory.BeanFactory)  ->  initBeanFactory((ConfigurableListableBeanFactory) beanFactory);
     *
     *     -5-  AnnotationAwareAspectJAutoProxyCreator#initBeanFactory(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)
     *
     *    1.传入配置类创建IOC容器，注册配置类，调用refresh() 刷新容器
     *    2.registerBeanPostProcessors(beanFactory);注册bean的后置处理器，方便拦截bean的创建
     *          先获取IOC容器中已经定义好的需要创建对象的所有BeanPostProcessor
     *          给容器中加别的 BeanPostProcessor
     *          优先注册实现了 PriorityOrdered 接口的 BeanPostProcessor
     *          然后在给容器中注册实现了 Ordered 接口的 BeanPostProcessor
     *          再注册没有实现优先级接口的 BeanPostProcessor
     *          注册 BeanPostProcessor ，实际就是创建 BeanPostProcessor 对象，保存在容器中
     *                  创建 internalAutoProxyCreator 的 BeanPostProcessor [AnnotationAwareAspectJAutoProxyCreator]
     *                          创建Bean实例
     *                          populateBean() 给bean的各种属性赋值
     *                          initializeBean()  初始化bean
     *                                  invokeAwareMethods() : 处理Aware 的方法回调
     *                                  applyBeanPostProcessorsBeforeInitialization()  应用后置处理器的 PostProcessorsBeforeInitialization
     *                                  invokeInitMethods() 执行自定义的初始化方法
     *                                  applyBeanPostProcessorsAfterInitialization()   应用后置处理器的 PostProcessorsAfterInitialization()
     *                          BeanPostProcessor [AnnotationAwareAspectJAutoProxyCreator] 创建完成
     *          把 BeanPostProcessor 注册到 BeanFactory 中 ： beanFactory.addBeanPostProcessor(postProcessor);
     *
     *      ------  以上是创建和注册  AnnotationAwareAspectJAutoProxyCreator 的过程  ------
     *
     *      AnnotationAwareAspectJAutoProxyCreator 是 InstantiationAwareBeanPostProcessor 类型的后置处理器
     *
     *              finishBeanFactoryInitialization(beanFactory);  对应初始化bean  完成  BeanFactory  初始化操作，创建剩下的单实例 bean
     *                      遍历获取容器中所有的 bean ，依次创建对象 getBean(beanName) -> doGetBean() -> getSingleton(beanName);
     *                      创建bean  ： 先从缓存中获取bean， 有则直接使用否则在创建，创建好的bean都会被缓存起来
     *                      createBean(beanName, mbd, args);
     *                          resolveBeforeInstantiation(beanName, mbdToUse);  解析BeforeInstantiation，希望后置处理器在此能返回一个对象代理，如果能返回代理对象就使用，不饿能就继续
     *                              后置处理器先尝试返回对象
     *                          doCreateBean(beanName, mbdToUse, args);  真正的去创建一个 bean 实例 和  上面创建的
     *
     *
     *
     *  目标方法执行
     *          容器中保存了组件的代理对象,也就是cglib 增强后的对象 ， 这个对象保存了增强器 目标对象等详细信息
     *          -1-  CglibAopProxy.DynamicAdvisedInterceptor#intercept(java.lang.Object, java.lang.reflect.Method, java.lang.Object[], org.springframework.cglib.proxy.MethodProxy)
     *                拦截目标逻辑：List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
     *                根据 ProxyFactory 队形获取  将要执行目标方法的 拦截器链
     *                如果没有拦截器链则直接执行目标方法，否则把需要执行的目标对象，目标方法，拦截器链等信息传入 CglibMethodInvocation  对象
     *
     *
     *  总结
     *          -1-   @EnableAspectJAutoProxy  开启AOP功能
     *          -2-   @EnableAspectJAutoProxy   会给容器中注册一个  AnnotationAwareAspectJAutoProxyCreator
     *          -3-   AnnotationAwareAspectJAutoProxyCreator  是一个后置处理器
     *          -4-   容器的创建流程
     *                  registerBeanPostProcessors(beanFactory); 注册后置处理器，创建 AnnotationAwareAspectJAutoProxyCreator 对象
     *                  registerBeanPostProcessors(beanFactory);  初始化剩下的单实例bean
     *                      创建业务逻辑逐渐和切面组件
     *                      AnnotationAwareAspectJAutoProxyCreator  拦截组件的创建过程
     *                      组件创建完成之后，判断组件是否需要增强，是则将切面的通知方法包装成增强器Advisor，给业务逻辑组件创建一个代理对象cglib
     *          -5-   执行目标方法
     *                  代理对象执行目标方法
     *                  CglibAopProxy.intercept() :  得到目标方法的拦截器链，即增强器包装成拦截器MethodInterceptor, 利用拦截器的请求机制，依次今日每一个拦截器进行执行
     *                              效果 ：
     *                                  正常执行： 前置通知 -> 目标方法 -> 后置通知 -> 返回通知
     *                                  异常执行： 前置通知 -> 目标方法 -> 后置通知 -> 异常通知
     *
     *
     *
     *
     *
     */
    private static void test01() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AOPConfig.class);
        MathCalculator bean = context.getBean(MathCalculator.class);
        bean.div(4, 2);
        //System.out.println(div);

    }
}
