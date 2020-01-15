package com.example.condition;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportSelector implements ImportSelector {

    /**
     *
     * @param importingClassMetadata  当前标注@Import注解类的所有注解信息
     * @return  导入到容器中的组件全类名
     */
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        //返回全类名 new String[]{Color.class,Person.class,User.class}
        return new String[0];
    }
}
