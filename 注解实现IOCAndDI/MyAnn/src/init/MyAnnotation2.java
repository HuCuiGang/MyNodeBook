package init;

import java.lang.annotation.*;

//配置注解使用位置
@Target({ElementType.TYPE,ElementType.METHOD})
////保留策略   保留在源代码上面，class文件，保留到运行的时候
@Retention(RetentionPolicy.RUNTIME)
@Inherited //标记此注解可以用于继承
public @interface MyAnnotation2 {

	// 属性名字为value的属性是特殊属性
	//如果只有value属性，配置的时候不需要写value的名字
	//如果有多个属性，但是只需要配置value属性，必须其它属性有默认值，才可以不写value的名字
	String value();
	
	int age() default 2;
	
}
