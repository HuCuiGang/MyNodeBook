package com.hcg.service.impl;

import com.hcg.dao.UserDao;
import com.hcg.factory.Autowired;
import com.hcg.factory.Resource;
import com.hcg.factory.Service;

import com.hcg.factory.Value;
import com.hcg.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	@Value("18")
	private String age;
	@Override
	public void hello() {
		System.out.println(userDao);
		System.out.println("age:"+age);
		
	}

}
