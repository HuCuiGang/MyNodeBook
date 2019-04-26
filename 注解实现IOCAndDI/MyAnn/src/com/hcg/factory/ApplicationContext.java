package com.hcg.factory;

public interface ApplicationContext {
	
	/**
	 * @param name
	 * @return
	 */
	public Object getBean(String name);

}
