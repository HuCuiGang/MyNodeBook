package com.hcg.factory;
import org.apache.commons.beanutils.BeanUtils;
import org.dom4j.Document;

import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author hcg
 * 
 *         2019-4-22
 */
public class ClassPathXmlApplicationContext implements ApplicationContext {

/*
	public static final String CONTEXT_LOCATION = "contextLocation";
	public static final String CONTEXT_SERVLETCONTEXT_NAME = ClassPathXmlApplicationContext.class
			.getName() + ":spring";
*/

	/**
	 * 保存对象
	 */
	private List<String> classNames = new ArrayList<String>();
	
	private Map<String, Object> contexs = new HashMap<String, Object>();
	
	public ClassPathXmlApplicationContext(String resourceName) {

		try {
			if(resourceName==null||resourceName.trim().equals("")){
				throw new RuntimeException("配置文件路径不能为空");
			}

			InputStream inputStream = null;
			//处理相对路径和绝对路径
			if(resourceName.contains("classpath:")){

				inputStream = this
							.getClass()
							.getClassLoader()
							.getResourceAsStream(resourceName.replace("classpath:",""));
			}else{
				inputStream = new FileInputStream(resourceName);
			}

			//解析XML配置文件
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputStream);
			//读取扫描包的路径
			Node node = document.selectSingleNode("//component-scan");
			if (node==null){
				throw new RuntimeException("没有配置扫描包");
			}
			String basePackage = node.valueOf("@base-package");
			String[] packages = basePackage.split(",");

			for (String p:packages) {
				scanPackage(p);
			}

			ioc();
			di();

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("加载配置文件失败");
		}

		System.out.println(classNames);
	}
	
	private void scanPackage(String packageName) {
		
		//获取类路径
		URL url = this.getClass().getClassLoader().getResource(replaceTo(packageName));
		
		//获取目标文件夹的路径
		String pathFile = url.getFile();
		File file = new File(pathFile);
		
		//获取指定文件夹下的文件和文件夹
		String[] fileList = file.list();
		
		for (String path : fileList) {
			
			File ecaFile = new File(pathFile+File.separator+path);
			
			if(ecaFile.isDirectory()) {
				//文件夹
				scanPackage(packageName+"."+ecaFile.getName());
			}else {
				
				if(ecaFile.getName().endsWith(".class")) {
					classNames.add(packageName+"."+ecaFile.getName().replace(".class", ""));
				}
				
			}
		}
		
	}

	//IOC将对象交给容器管理
	private void ioc(){
		for (String className:classNames) {
			try {
				//加载类
				Class clazz= Class.forName(className);
				//判断是否有IOC注解
				if(clazz.isAnnotationPresent(Service.class)||clazz.isAnnotationPresent(Controller.class)
					||clazz.isAnnotationPresent(Commpont.class)||clazz.isAnnotationPresent(Repository.class)){

					String beanName="";
					Object object = clazz.newInstance();
					//获取目标注解
					Service service= (Service) clazz.getAnnotation(Service.class);
					//获取注解的Value值
					if (service==null){
						Commpont commpont= (Commpont) clazz.getAnnotation(Commpont.class);
						if (commpont==null){
							Controller controller= (Controller) clazz.getAnnotation(Controller.class);
							if (controller==null){
								Repository repository= (Repository) clazz.getAnnotation(Repository.class);
								if (repository==null){
									throw new RuntimeException("未找到Ioc注解");
								}else{
									beanName = repository.value();
								}
							}else{
								beanName = controller.value();
							}
						}else{
							beanName = commpont.value();
						}
					}else {
						beanName = service.value();
					}
					//存储多个引用
					//判断bename是否为空值，按注解的value值储存引用
					if(!beanName.equals("")){
						contexs.put(beanName,object);
					}
					//按照类的全限定名储存
					contexs.put(clazz.getName(),object);
					//按照所有的接口储存一份
					//获取该属性的所有接口
					Class[] interfaces = clazz.getInterfaces();
					if (interfaces!=null){
						for (Class i : interfaces) {
							contexs.put(i.getName(),object);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				new RuntimeException("创建"+className+"失败");
			}

		}

	}
	private void di(){
		//多个类实现相同接口，在spring中 会按照类型注入报错，因为他不知道要注入哪个类。
		for (Map.Entry<String, Object > entry : contexs.entrySet()) {
			try {
				//Ioc对象
				Object bean = entry.getValue();

				//获取IOC对象的所有属性
				Field[] fields = bean.getClass().getDeclaredFields();

				//遍历每一个属性
				for(Field field:fields){

					String beanName ="";
					if (field.isAnnotationPresent(Autowired.class)){
						//按照类型注入
						beanName = field.getType().getName();
					}else if (field.isAnnotationPresent(Resource.class)){
						//按Resource的name属性值注入
						Resource resource = field.getAnnotation(Resource.class);
						beanName = resource.name();
					}else if (field.isAnnotationPresent(Value.class)){
						//注入普通属性
						//获取Value注解的值
						Value value = field.getAnnotation(Value.class);
						String myValue = value.value();
						//暴力反射注入
						//开启访问权限
						//此处只考虑了两种类型的转换，如需转换多种类型需要添加判断处理
						field.setAccessible(true);
						if (field.getType().getName().equals(Integer.class.getName())){
							field.set(bean,Integer.valueOf(myValue));
						}else{
							field.set(bean,myValue);
						}
					}

					//注入对象
					if (!beanName.equals("")) {
						//暴力反射注入
						//开启访问权限
						field.setAccessible(true);
						Object target = getBean(beanName);
						field.set(bean, target);
					}
				}
			}catch (Exception e){
				e.printStackTrace();
				throw new RuntimeException("注入属性失败");
			}
		}
	}


	
	private String replaceTo(String path) {
		return path.replaceAll("\\.", "/");
		
	}

	@Override
	public Object getBean(String name) {
		Object object = contexs.get(name);
		if (object==null){
			throw new RuntimeException("没有找到指定的类");
		}
		return object;
	}


}
