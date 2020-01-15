package com.example.test;

import com.example.bean.Car;
import com.example.bean.Person;
import com.example.config.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.Map;

public class MainTest {

    public static void main(String[] args) {
        test03();
    }

    /**
     *@Profile 是  spring为我们提供的可以根据当前环境，动态的激活和切换一系列组件的功能
     * @Profile("default")  为默认配置的环境
     *   -1-  激活环境
     *       1.在项目运行前加上参数   -D spring.profiles.active=...
     *       2.使用代码的方式激活
     *              创建一个无参的  AnnotationConfigApplicationContext
     *              设置要激活的环境  context.getEnvironment().setActiveProfiles("test","dev");
     *              注册配置类  context.register(ProfileConfig.class);
     *              启动刷新容器  context.refresh();
     */
    private static void test11() {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        context.getEnvironment().setActiveProfiles("test","dev");
        context.register(ProfileConfig.class);
        context.refresh();

        String[] names = context.getBeanNamesForType(DataSource.class);
        for (String s : names) {
            System.out.println(s);
        }


        context.close();

    }

    /**
     * @AutoWried(required=false)  用在属性，构造器，参数，方法中
     *
     *      默认优先按照类型去容器中找对应的组件，如果有多个相同类型的组件，则将
     *      属性的名称作为组件的id去容器中查找
     *
     *      若要指定某一个组件可以使用@Qualifier("bookDao") 明确指定，加在有@AutoWried(required=false)注解的属性上
     *
     *      使用首选的自动装配组件@Primary   在配置类的方法上添加该注解,也可以使用@Qualifier("bookDao") 明确指定
     *
     *      -2-
     *      spring还支持使用@Resource(JSR250) 和 @Inject(JSR330)
     *
     *              在需要自动装配的属性上添加该注解即可，@Resource默认按照属性名称装配
     *              @Resource(name="book2")  可以明确指定装配，没有@Qualifier/@Primary
     *
     *              使用@Inject要加入依赖javax.inject 和@AutoWried差不多，但没有required=false
     *
     *       -3-  注解标注在setter方法上，spring容器创建当前对象就会调用方法完成赋值，方法使用的参数，自定义类型的值从ioc容器中获取
     *                  @Bean  标注的方法创建对现象的时候，方法中可以传入参数，该参数也是从ioc容器中获取，@AutoWried可以省略
     *       -4-  标注在有参构造器上，构造器的参数也是在ioc容器中获取
     *                  如果组件只有一个有参构造器，这个有参构造器的@AutoWried可以省略
     *
     *       -5-  标注在有参构造器的参数上，也是在ioc容器中获取
     *
     *
     *  自定义组件想要使用spring容器底层的一些组件(ApplicationContext  BeanFactory  )
     *  可以实现xxxAware接口
     */
    private static void test10() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AutoWriedConfig.class);
        Object red = context.getBean("red");
        System.out.println(red);
        System.out.println(context);
    }

    /**
     * @Value("name")
     *   person 类中使用该注解 可以给属性赋值
     *      () 中可以写基本数值，spEL(#{}),也可以写${} 取出配置文件中的值
     *
     *      ${}  在配置类中添加@PropertySource(value = {"classpath:/person.properties"})注解
      */
    private static void test09() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PropertyValueConfig.class);
        /*String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }*/
        Person person = (Person) context.getBean("person");
        System.out.println(person);

        ConfigurableEnvironment environment = context.getEnvironment();
        String name = environment.getProperty("person.name");
        System.out.println(name);
    }

    /**
     * bean的生命周期,  新建BeanLifeCycleConfig配置类
     *      -1- 指定初始化和销毁方法
     *        xml配置文件中添加  init-method="" destroy-method=""  或者在配置类中的@Bean中指定
     *        对象在完成创建之后进行初始化，容器关闭时销毁
     *        当在配置类中加上@Scope(value = "prototype")时，在容器获取时才会 创建对象 调用初始化方法，容器不会执行销毁方法
     *        若要销毁 可以直接调用销毁方法
     *
     *      -2- Car类 实现  implements InitializingBean, DisposableBean  两个接口
     *
     *      -3- @PostConstruct  在备案创建完成并且属性赋值赋值完成，执行初始化方法
     *            @PreDestroy  在容器销毁bean之前通知我们进行清理工作
     *
     *      -4-  BeanPostProcessor  bean的后置处理器，在 bean 初始化前后进行一些处理工作
     *          public class MyBeanPostProcessor implements BeanPostProcessor
     *          原理分析：
     *             beans/factory/support/AbstractAutowireCapableBeanFactory.java
     *
     *             protected void populateBean(String beanName, RootBeanDefinition mbd, BeanWrapper bw) {}  给bean属性赋值
     *
     *             wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);  遍历容器中所有的BeanPostProcessors
     *             invokeInitMethods(beanName, wrappedBean, mbd);  初始化执行
     *             applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
     *
     *           spring底层对BeanPostProcessor的使用
     *             查看 ApplicationContextAwareProcessor  实现类
     *
     *             可以使用Car类实现 ApplicationContextAwareProcessor 接口， 定义applicationContext 在override方法中 this.applicationContext = applicationContext
     *
     *          bean赋值，注入其他组件，@Autowired，生命周期注解功能，@Async...都是用BeanPostProcessor
     *
     *
     */
    private static void test08() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeanLifeCycleConfig.class);
        System.out.println("context is successfully loaded.");
        Car car = context.getBean(Car.class);
        //关闭容器
        context.close();
    }

    /**
     *使用Spring提供的FactoryBean 工厂bean
     *
     *      -1-  新建 ColorFactoryBean 类 实现FactoryBean<Color> 接口
     *      -2-  在配置类中注册该类
     */
    private static void test07() {
        ApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
        /*String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }*/
        //获取Color对象
        Object o1 = context.getBean("colorFactoryBean");
        Object o2 = context.getBean("colorFactoryBean");
        System.out.println(o1 == o2);

        //获取ColorFactoryBean对象
        Object o3 = context.getBean("&colorFactoryBean");
        System.out.println(o3.getClass());
    }

    /**
     *
     * 给容器中注册组件的三种方法
     *      -1-  包扫描+组件标注注解 @Controller @Service @...
     *      -2-  @Bean  导入第三方包里面的组件
     *      -3-  @Import  快速给容器中导入一个组件
     *          @Import({Color.class,...})
     *          ImportSelector  返回需要导入的组件的全类名数组  public class MyImportSelector implements ImportSelector
     *          ImportBeanDefinitionRegistrar  MyImportBeanDefinitionRegistrar 实现 ImportBeanDefinitionRegistrar 接口
     *      -4-  使用Spring提供的FactoryBean 工厂bean
     *           test07
     */
    private static void test06() {

        ApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
    }

    /**
     * @Conditional({Condition})  按照一定的条件进行注册，满足条件则注册bean,即可放在方法上也可以放在类上
     *
     *          新建condition包，新建两个类继承org.springframework.context.annotation.Condition;
     *          编写匹配条件，在配置类中的方法上添加@Conditional(WindowsCondition.class)
     */
    private static void test05() {
        ApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
        Map<String, Person> beans = context.getBeansOfType(Person.class);
        System.out.println(beans);

        /*Environment environment = context.getEnvironment();
        String property = environment.getProperty("os.name");
        System.out.println(property);*/
    }

    /**
     * @Scope
     *              prototype 多实例  不会在ioc容器启动时调用方法，而是每次获取时调用方法创建对象
     *              singleton  单实例  ioc容器会在启动时调用方法创建对象，把对象放放到容器中，以后每次获取都是map.get()
     *              request  同一次请求创建一个实例
     *              session 同一次session创建一个实例
     *
     * @Lazy  懒加载：用在单实例bean中，容器启动先不创建对象，第一次使用或者获取时才去创建对象进行初始化
     */
    private static void test04() {
        //单实例会在容器启动时调用MyConfig中的newInstance()，多实例不会
        ApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
        //多实例会在容器创建完成之后调用MyConfig中的newInstance方法
        //有多少个context.getBean("");就会调用多少次newInstance方法
        context.getBean("");
    }

    /**
     * 测试@ComponentScan(value = "com.example") 注解
     *
     * @ComponentScan(value = "com.example",excludeFilters = {@ComponentScan.Filter(
     *         type = FilterType.ANNOTATION,value = {Controller.class, Service.class}
     * )})
     *
     * @ComponentScan(value = "com.example",includeFilters = {@ComponentScan.Filter(
     *         type = FilterType.ANNOTATION,value = {Controller.class, Service.class}
     * )},useDefaultFilters = false)
     *
     * 使用自定义过滤规则MyTypeFilter implements TypeFilter
     * @ComponentScan(value = "com.example",includeFilters = {@ComponentScan.Filter(
     *         type = FilterType.CUSTOM,value = {MyTypeFilter.class}
     * )},useDefaultFilters = false)
     *
     */
    private static void test03() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
        //Object bean = context.getBean("");

    }

    /**
     * 使用@Bean 来注入，@Bean("id") 可以指定id
     */
    private static void test02() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Person person = context.getBean(Person.class);
        System.out.println(person.toString());

        String[] names = context.getBeanNamesForType(Person.class);
        for (String name : names) {
            System.out.println(name);
        }
    }

    /**
     * 使用beans.xml配置完成注入
     */
    private static void test01() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        Person person = (Person) context.getBean("person");
        System.out.println(person.toString());
    }
}
