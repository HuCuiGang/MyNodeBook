package init;

//注解的属性
public @interface MyAnnotation {
	String name() default "王五";
	int age();
	Class clazz();
	int[] ss();
	MyAnn my();
}
