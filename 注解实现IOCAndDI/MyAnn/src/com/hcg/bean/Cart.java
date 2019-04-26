package com.hcg.bean;

import com.hcg.factory.Autowired;
import com.hcg.factory.Commpont;
import com.hcg.factory.Value;

@Commpont
public class Cart {
	@Value("∫Ï∆Ïh7")
	private String name;
	
	@Autowired
	private Brand brand;

	public Brand getBrand() {
		return brand;
	}
	public String getName() {
		return name;
	}
	
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
