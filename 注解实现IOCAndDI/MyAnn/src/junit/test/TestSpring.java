package junit.test;


import com.hcg.service.UserService;

import com.hcg.factory.ClassPathXmlApplicationContext;
import org.junit.Test;

public class TestSpring {
	
	@Test
	public void testSpring(){

		ClassPathXmlApplicationContext	classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		UserService userService = (UserService) classPathXmlApplicationContext.getBean("userService");

		userService.hello();
	}

}
