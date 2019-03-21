show databases;

show tables;

drop database if exists learn;
create database learn;

drop table if exists learn.student;
create table learn.student
(
  id           int primary key auto_increment comment '主键',
  num          varchar(255) unique comment '学号',
  name         varchar(255) comment '姓名',
  departmentId int comment '系号'
);

insert into learn.student
values (null, 'z1111', '李华', '1');
insert into learn.student
values (null, 'z1112', '李宁', '1');
insert into learn.student
values (null, 'z1113', '王菲', '1');
insert into learn.student
values (null, 'z1115', null, '2');
insert into learn.student
values (null, 'z1116', '张五', '2');
insert into learn.student
values (null, 'z1117', null, null);
insert into learn.student
values (null, 'z1118', '刘搜', null);


set foreign_key_checks = 0;
drop table if exists learn.department;
create table learn.department
(
  id   int primary key auto_increment comment '主键',
  name varchar(255)
);
set foreign_key_checks = 1;

insert into learn.department
values (null, '计算机');
insert into learn.department
values (null, '经管');
insert into learn.department
values (null, null);

# 追加外键
alter table learn.student
  add constraint student_fk_departmentId
    foreign key (departmentId)
      references learn.department (id);

# 查看表结构
desc learn.student;

# 查看建表语句
show create table learn.student;

select *
from learn.student;

-- 显示learn数据库的所有表和视图
show full tables from learn;

-- 显示learn库中的表的信息
show table status from learn;

-- 显示名为student表的信息
show table status where Name = 'student';

-- 显示表的所有列信息（包含comment信息），当不加full时与desc的作用相同
show full columns from learn.student;

desc learn.student;
-- 显示变量
show variables;

-- 内连接查询
select s.name, d.name
from learn.student s
       join learn.department d
            on s.departmentId = d.id;

-- 左外连接
select *
from learn.student s
       left join learn.department d
                 on s.departmentId = d.id;

-- 右外连接
select *
from learn.student s
       right join learn.department d
                  on s.departmentId = d.id;

-- 全连接
select *
from learn.student s
       left join learn.department d
                 on s.departmentId = d.id
union
select *
from learn.student s
       right join learn.department d
                  on s.departmentId = d.id;

-- 事物处理
start transaction;

set foreign_key_checks = 0;
delete
from db_school.student
where id = 3;

savepoint a;

update db_school.student
set name = '张ss'
where id = 2;

savepoint b;

commit;

set foreign_key_checks = 1;

rollback to a;
select *
from db_school.student;

select loc, count(*)
from db_ip.ip_range
group by loc;