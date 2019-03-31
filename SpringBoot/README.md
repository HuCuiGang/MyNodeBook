# SpringBoot
## 一、介绍
![1](assets/1.png)
![2](assets/2.png)

## 二、入门
pom.xml加入配置
---
![3](assets/3.png)
---
![4](assets/4.png)
---
@SpringBootApplication  //springBoot的核心注解，主要用于开启自动配置
方式二
---
![5](assets/5.png)

## 四、SpringBoot的核心
入门口类和SpringApplication

一个springboot项目都有一个*Application的入口类，入口类有一个主函数，这是一个标准的java程序入口。

![6](assets/6.png)
---
@SpringBootApplication注解是springBoot的核心注解
---
![7](assets/7.png)
![8](assets/8.png)

---
SpringBoot项目推荐使用springBootConfiguration替代Configuration
@EnableAutoConfiguration
 启动自动装配，该注解会使springboot根据jar包自动进行配置。
---
![10](assets/10.png)
项目中就会引入springmvc的依赖，springboot就会自动配置springmvc和tomcat。
![11](assets/11.png)
## 五、官方的start
如果想关闭某个技术的自动配置
![12](assets/12.png)

## 六、ssm整合
### 6.1配置springmvc的环境
![13](assets/13.png)

### 6.2导入jar包
![14](assets/14.png)
![15](assets/15.png)

### 6.3配置数据源
![16](assets/16.png)
![17](assets/17.png)
### 6.4配置sqlSessionFactory和插件
![18](assets/18.png)
![19](assets/19.png)
### 6.5配置扫描包
![20](assets/20.png)
## 七、ssjpa整合
![21](assets/21.png)
![22](assets/22.png)
## 八、SpringBoot日志默认设置
![23](assets/23.png)
---
Springboot默认在info级别

可以配置日志文件  会自动加载日志配置文件的配置
---
![24](assets/24.png)





