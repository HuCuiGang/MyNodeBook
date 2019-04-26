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

方式二：使用空格

select last_name 姓，first_name 名 from employees；

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

#### 10.拼接,concat函数
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

## 八、模糊查询
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

### 3.in
含义：判断某个字段的值是否属于in列表中的某一项

特点：	

	1.使用in提高语句简洁度
	2.in列表的值类型必须一致或兼容
	3.不支持通配符

案例：查询员工的工种编号是 IT_PROG、AD_VP、AD_PRES中的一个员工名和工种编号

`select last_name,job_id from employees where job_id = 'IT_PROT' OR job_id = 'AD_VP' OR job_id = 'AD_PRES';`

--------------------------------
`select last_name,job_id from employees where job_id IN('IT_PROT','AD_VP','AD_PRES') ;`

### 4.is null

案例1：查询没有奖金的员工名和奖金率

`select last_name,commission_pct from employees where commission_pct IS NULL;`

安全等于 <=> （判断是否等于）

``select last_name,commission_pct from employees where commission_pct <=> NULL;``

案例2：查询有奖金的员工名和奖金率

`select last_name,commission_pct from employees where commission_pct IS NOT NULL;`

案例3：查询工资为12000的员工名

`select last_name from employees where salary <=> 12000;`

-------------------------------------------
查询员工号为176的员工的姓名和部门号和年新

`select last_name,department_id,salary*12*(1+ifnull(commission_pct,0)) AS 年薪 from employees;`

## 九、进阶3:排序查询
引入：

	select * from employees;

语法： 

	select 查询列表 from 表 where 帅选条件 order by 排序列表 asc|desc

特点：

	1.asc代表的是升序，desc代表的是降序。
	如果不写，默认是升序。
	2.order by子句中可以支持单个字段、多个字段、表达式、函数、别名
	3.order by子句一般是放在语句的最后面，limit子句除外

案例1：查询员工信息，要求工资从高到低排序

`select * from employees order by salary desc;`

升序：

`select * from employees order by salary asc;`

`select * from employees order by salary;`


案例2：查询部门编号>=90的员工信息，安入职时间的先后顺序排序（添加帅选条件）

`select * from employees where department_id>=90 order by hiredate ASC;`

案例3：安年新的高低显示员工的信息和年薪（按表达式和别名排序）

`select *,salary*12*(1+ifnull(commission_pct,0)) 年薪 from order by salary*12*(1+ifnull(commission_pct,0)) desc;`

`select *,salary*12*(1+ifnull(commission_pct,0)) 年薪 from order by 年薪 desc;`

案例4：按姓名的长度显示员工的姓名和工资（按函数排序）

`select length（last_name） 字节长度，last_name,salary from employees order by length(last_name) desc;`

案例6： 查询员工信息，要求先按工资升序，再按员工编号降序（安多个字段排序）

`select * from employees order by salary asc,employees_id desc;`

## 十、分组查询
语法：

	select 分组函数，列（要求出现在group by的后面）
	from 表
	where 筛选条件
	group by 分组的列表
	order by 子句

注意：

	查询列表必须特殊没要求是分组函数和group by后出现的字段

特点：

	1.分组查询中的筛选条件分为两类
		       数据源
	分组前筛选	 原始表
	分组后筛选	 分组后的结果集

	分组函数做条件肯定是放在having子句中
	能用分组前帅选的，就优先考虑使用分组前筛选

	2.group by子句支持单个字段分组，多个字段分组（多个字段之间用逗号隔开没有顺序要求），表达式

	3.也可以添加排序(排序放到整个分组查询的最后)

案例1：查询每个工种的最高工资

`select max（salary），job_id from employees group by job_id;` 

案例2：查询每个位置上的部门个数

`select count（*),location_id from department group by location_id;`

添加筛选条件

案例1：查询邮箱中包含a字符的，每个部门的平均工资

`select avg（salary），department_id from employees where email like ’%a%‘ group by department_id`

案例2：查询有奖金的每个领导手下员工的最高工资

`select max（salary），manager_id from employees where commission_pct is not null group by manager_id;`

添加复杂的筛选条件

案例1：查询哪个部门的员工个数>2

	1.查询每个部门的员工给个数
	select count(*),department_id from employees group by department_id;
	2.根据1.的结果进行帅选，查询哪个部门的员工个数>2
	select count(*),department_id from employees group by department_id having count(*)>2;

案例2：查询每个工中有奖金的员工的最高工资>12000的工种编号和最高工资

`select max（salary），job_id from employees where commission_pct is not null group by job_id having max(salary)>12000;`

案例3：擦呼吸呢领导编号>102的每个领导手下的对低工资>5000的领导编号是哪个，以及其最低工资

	1.查询每个领导手下的员工固定最低工资
	select min（salary），manager from employees group by manager_id
	2.添加筛选条件 manager>102
	select min（salary），manager from where manager_id>102 employees group by manager_id 
	3.添加筛选条件 最低工资>5000
	select min（salary），manager from where manager_id>102 employees group by manager_id having min(salary)>5000;

### 按表达式或函数分组
案例：按员工姓名的长度分组，查询每一组的员工个数，筛选员工个数>5的有哪些

	1.查询每个长度的员工个数
	select count(*),length(last_name) len_name
	from employees
	group by length(last_name);
	2.添加筛选条件 
	elect count(*),length(last_name) len_name
	from employees
	group by length(last_name)
	having count(*)>5;

### 按多个字段分组
案例：查询每个部门每个工种的员工的平均工资

`select avg(salary),department_id,job_id from employees group by job_id,department_id;`

### 添加排序
案例：查询每个部门每个工种的员工的平均工资，并按平均工资的高低显示
```
select avg（salary） a，department_id,job_id 
from employees 
where department_id is not null
group by job_id,department_id
having avg(salary)>10000
order by a  desc;
```


