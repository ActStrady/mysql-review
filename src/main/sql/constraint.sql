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
  departmentId int not null comment '系名'
);

drop table if exists learn.department;
create table learn.department
(
  id int primary key auto_increment comment '主键'
);

# 追加外键
alter table learn.student
  add constraint student_fk_departmentId
    foreign key (departmentId)
      references learn.department (id);

# 查看表结构
desc learn.student;

# 查看建表语句
show create table learn.student;

insert into learn.student
values (null, 'jjj', '亏', 1);

select *
from learn.student;

