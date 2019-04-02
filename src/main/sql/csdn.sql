-- csdn泄露 数据库
drop database if exists db_csdn;
create database db_csdn;

-- csdn 用户信息表
drop table if exists db_csdn.userinfo;
create table db_csdn.userinfo
(
    id       int auto_increment primary key comment '主键',
    username varchar(255) not null comment '用户名',
    password varchar(255) not null comment '密码',
    email    varchar(255) not null unique comment '邮箱'
) comment '用户信息表';

-- 导入到数据库
load data local infile 'E:\\Projects\\IdeaProjects\\MySQL\\mysql-review\\src\\main\\resources\\csdn-new.sql'
    into table db_csdn.userinfo
    fields terminated by '¦'
    (username, password, email)
    set id = null;

truncate table db_csdn.userinfo;