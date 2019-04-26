package init;
@MyAnn
@MyAnnotation2("")
public class Demo01 {

	@MyAnn
	private String num;

	@MyAnn
	private static Integer number;

	@MyAnn
	@MyAnnotation(age = 10, clazz = Demo01.class, my = @MyAnn, ss = {
			1, 2 })
	public static void fun1(@MyAnn String name) {

	}

	@MyAnn
	@MyAnnotation(age = 10, clazz = Demo01.class, my = @MyAnn, name = "xxx", ss = {
			1, 2 })
	@MyAnnotation2("")
	public void fun2() {

		@MyAnn
		String a;
	}

}