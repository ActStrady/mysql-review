show databases;

show tables;

drop database if exists learn;
create database learn;

drop table if exists learn.student;
create table learn.student (
  id int primary key auto_increment comment '主键',
  num varchar(255),
  name varchar(255),
  departmentId int not null,
  foreign key(departmentId)
    references learn.department(id)
);

desc learn.student;
show create table learn.student;
insert into learn.student
  values (null, 'jjj', '亏', 1);

select *
from learn.student;

drop table if exists learn.department;
create table learn.department (
  id int primary key auto_increment
)