# MySQL
## 一、Mysql的启动和停止
停止：`net stop mysql`
启动：`net start mysql`

## 二、服务端的登录和退出
登录：`mysql （-h localhost -P 3306） -u root -p
退出：exit或者CTRL+C`

## 三、mysql常见命令
1.显示数据库：`show databases；`

2.使用指定的数据库：`use test（数据库名）；`

3.查看数据库中所有的表：`show tables；show tabals from mysql（数据库名）；`

4.查看现在使用的数据库：`select database（）；`

5.创建表：`create tabale stuinfo（
	id int，
	name varchar（20））；`

6.查看表结构：`desc stuinfo；`

7.查看表中所有的数据：`select * from stuinfo;`

8.在表中加入数据：`insert into stuinfo（id，name）value（1，‘john’）；`

9.修改数据：`update stuinfo set name='xiaoli' where id=1;`

10.删除表中的数据：`delete from stuinfo where id=1；`

## 四、查看数据库版本
方式一：登录到mysql服务器
mysqlselect version（）；

方式二：没有登录到mysql服务器
dos命令：mysql -v

## 五、SQL的语句规范
1.不区分大小写，但建议关键子大写，表名、列名小写。

2.每条命令最好用分号结尾

3.每条命令根据需要，可以进行缩进或换行。

4.注释

	单行注释：#注释文字
	单行注释：--注释文字
	多行注释：/*注释文字*/

## 六、进阶1：基础查询
语法：select 查询列表 from 表名；
特点：
	1.查询列表可以是：表中的字段、常量值、表达式、函数
	2.查询的结果是一个虚拟表格

#### 1.查询表中的单个字段
`select last_name from employees;`
#### 2.查询表中的多个字段
`select last_name,salary,email from employees;`
#### 3.查询表中的所有字段
`select * from employees;`

#### 4.查询常量值
`select 100；`

`select ‘john’；`
#### 5.查询表达式
`select 100%98；`
#### 6.查询函数
`select version（）；`
#### 7.起别名
1.便于理解

2.如果要查询的字段有重名的情况，使用别名可以区分开来。

方式一：使用AS

select 100%98 AS 结果;

select last_name AS 姓，first_name AS 名 from employees;
---
方式二：使用空格

select last_name 姓，first_name 名 from employees；
---
案例：查询salary，显示结果为 out put

`select salary AS “out put” from employees;`

#### 8.去重
案例：查询员工表中涉及到的所有的部门编号
`select distinct  depertment_id from employees；`

#### 9.+号的作用
mysql中的+号只有一个功能：运算符

select 100+90；两个操作数都为数值型，则做加法运算

select ’123‘+90；其中一个为字符型，试图将字符型转换成数值型，如果成功，则继续做加法运算。

select ’john‘+90；如果转换失败，则将字符型数值转换成0

#### 10.拼接
`select concat（'a','b','c'） AS 结果；`

`select concat (last_name,first_name) AS 姓名 from employees；`

案例：查询员工名和姓连接成一个字段，并显示为 姓名

`select last_name+first_name AS 姓名 from employees；`

#### 11.ifnull（，）

## 七、条件查询
语法：select 查询列表 from 表明 where 筛选条件；
分类：

	一、按条件表达式筛选
	条件运算符：> < = != <> >= <=

	二、按逻辑表达式筛选
	逻辑运算符：、
	作用：用于连接条件表达式
		&& || ！
		and or not
	&&和and：两个条件都为true，结果为true，反之为flase
	||或or：只要有一个条件为true，结果为true，反之为false
	！或not：如果连接的条件本身为false，结果为true，反之为false

	三、模糊查询
		like
		between and
		in
		is null

#### 1.按条件表达式筛选
案例1：查询工资>12000的员工信息

`select * from employees where salary>12000;`

案例2：查询部门编号不等于90号的员工名和部门编号

`select last_name,department_id from employees where department_id<>90;`

#### 2.按逻辑表达式筛选
案例1：查询工资在10000到20000之间的员工名、工资、奖金。

`select last_name,salary,commission_pct from employees where salary>=10000 and salary<=20000;`

案例2：查询部门编号不是在90到110之间，或者工资高于15000的员工信息。
`select * from employees where department_id<90 or department_id>110 or salary>15000;`

`select * from employees where not(department_id>90 and department_id<=110) or salary>15000;`

## 三、模糊查询
like
特点：

	1.一般和通配符搭配使用
	通配符：
	% 任意多个字符
	_ 任意单个字符

between and

in

is null | is not null

### 1.like
案例1：查询员工名中包含字符a的员工信息

`select * from employees where last_name like '%a%';`

案例2:查询员工名中第三个字符为n，第五个字符为l的员工名和工资

`select last_name，salary from emloyees where last_name like '__n_l%';`

案例三：查询员工名中第二个字符为_的员工名

转义

```
select last_name from employees where last_name like '_\_%';

select last_name from employees where last_name like '_$_%' escape '$';

```
### 2.between and
案例1：查询员工编号在100到120之间的员工信息

`select * from employees where emloyee_id>=100 and employees_id<=120;`

`select * from employees where emloyee_id between 100 and 120;`
