# SQL 多表查询
含义：又称连接查询，当查询的字段来自多个表时，就会用到连接查询

笛卡尔乘积现象：表1 有M行，表2有n行，结果为=m*n行。

发生原因：没有有效的连接条件
如何避免：添加有效的连接条件

分类：

    按年代分类：
        sql192标准：仅支持内连接
        sql199标准【推荐】：支持内连接+外链接（左外和右外）+交叉连接
    
    按功能分类：
        内连接：
            等值连接
            非等值连接
            自连接
        外链接：
            左外连接
            右外连接
            全外连接
        交叉连接

`select name,boyName from boys,beauty where beauty.boyfirend_id=boys.id;`

## 一、sql192标准
### 1.等值连接

    1.多表等值连接的结果为多表的交集部分
    2.n表连接，至少需要n-1个连接条件
    3.多表的顺序没有要求
    4.一般需要对表取别名
    5.可以搭配前面介绍的所有子句使用，如：排序、分组、筛选

案例1：查询女神名和对应的男神名
```
select name,boyName 
from boys,beauty 
where beauty.boyfirend_id=boys.id;
```

案例2：查询员工名和对应的部门名
```
select last_name,department_name 
from employees,departments
where employees.department_id=department.department_id;
```

案例3：查询员工名、工种号、工种名
```
select last_name,employees.job_id,job_title
from employees,jobs
where employees.job_id=jobs.job_id;
```
#### 为表起别名

    1.提高语句的简介度
    2.区分多个重名的字段

注意：如果为表起了别名，原表名就不起作用了
```
select e.last_name,e.job_id,j.job_title
from employees e,jobs j
where e.job_id=j.job_id;
```
#### 可以加筛选？
案例1：查询有奖金的员工名、部门名
```
select last_name,department_name,commission_pct
from emloyees e,departments d
where e.department_id=d.department_id
and e.commission_pct is not null;
```
案例2：查询城市名中第二个字符为o的部门名和城市名
```
select department_name,city
from location l,departments d
where d.location_id=l.location_id
and city like '%_o%';
```
#### 可不可以加分组？
案例1：查询每个城市的部门个数
```
select count（*） 个数，city
from departments d,location l
where d.location_id=l.location_id
group by city;
```
案例2：查询有奖金的每个部门的部门名和部门的领导编号和该部门的最低工资
```
select department_name，d.manager_id,min(salary)
from departments d,employees e 
where d.department_id=e.department_id
and commission is not null
group by department_name,d.manager_id;
```
#### 可以加排序
案例：查询每个工种的工种名和员工的个数，并且按员工个数降序
```
select job_title,conut(*)
from employees e,jobs j
where e.job_id=j.job_id
group by job_title
order by count()* desc；
```

### 三表连接
案例：查询员工名、部门名和所在的城市
```
select last_name,department_name,city
from employees e,departments d ,location l
where e.department_id =d.department_id
and d.location_id=l.location_id;
```

### 2.非等值连接
案例1：查询员工的工资和工资级别
```
select salary，grade_level
from employees e,job_grades g
where salary between g.lowest_sal and g.highest_sal
and g.greade_level='A';
```

### 3.自连接

案例：查询 员工名和上级的名称
```
select e.employees_id,e.last_name,m.employees_id,m.last_name
from employees e,employees m
where e.manager_id=m.employee_id;
```

## 进阶六
###sql199
    语法：
    select 查询列表
    from 表一 别名 （连接类型）
    join 表二 别名 
    on 连接条件条件
    where 筛选条件
    group by 分组
    hiving 筛选条件
    order by 排序列表（dec，desc） 

    内连接：inner
    外链接
        左连接：left join 【outer】
        右连接：right join
        完全外连接：full 
    交叉连接： cross


### 非等值连接
---
select stalary,grade_level
from employees e
join job_grades g 
on e.salary between g.lowest_sal and g.highest_sal




