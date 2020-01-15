package com.example.ext;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.EventListener;

/**  扩展原理 ：
 *      -1-  BeanFactoryPostProcessor
 *              BeanPostProcessor  :  bean 的后置处理器  在bean创建对象初始化前后进行拦截工作的
 *              BeanFactoryPostProcessor  ：  BeanFactory的后置处理器，在BeanFactory  标准初始化之后调用
 *                      所有的bean定义已经加载到 BeanFactory 中，但是 bean的实例并没有被创建
 *
 *      -2- BeanDefinitionRegistryPostProcessor
 *              extends BeanFactoryPostProcessor ,在所有的bean定义信息将要被加载，bean实例还没有被创建时调用
 *              在  BeanFactoryPostProcessor  之前执行 ,可以在额外添加一些组件
 *
 *
 *     -3-  ApplicationListener  监听容器中发布的事件  实现  ApplicationListener  接口
 *               写一个监听器来监听某个事件
 *               将监听器放到容器中
 *               只要触发了事件就会执行相应操作
 *
 *     -4-  @EventListener(classes = ApplicationEvent.class)   也可以监听事件  放在方法上面
 *              要想拿到事件 可以给方法传入参数 ApplicationEvent event
 *
 */
public class ExtTest {
    public static void main(String[] args) {
        test01();
    }


    private static void test01() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ExtConfig.class);
        //发布事件
        context.publishEvent("");
        context.close();
    }
}
