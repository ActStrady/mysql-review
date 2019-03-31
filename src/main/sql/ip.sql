-- ip 数据库
drop database if exists db_ip;
create database db_ip;

-- ip范围地址表
drop table if exists db_ip.ip_range;
CREATE TABLE db_ip.ip_range
(
    id  int auto_increment primary key comment '主键',
    min varchar(255) not null comment '范围左ip',
    max varchar(255) not null comment '范围右ip',
    loc varchar(255) not null comment '地址'
);

-- 清空
truncate table db_ip.ip_range;

-- 查看数量
select count(*)
from db_ip.ip_range;

-- 从文件导入
load data local infile 'E:\\Projects\\IdeaProjects\\MySQL\\mysql-review\\src\\main\\resources\\ip-new.txt'
    into table db_ip.ip_range
    fields terminated by '¦'
    (min, max, loc)
    set id = null;

-- 查询指定ip的地址
-- inet_aton函数，用来把ip地址转换为数值
-- inet_ntoa函数，用来把数值转化为ip
select loc
from db_ip.ip_range
where inet_aton('12.3.5.255') between inet_aton(min) and inet_aton(max);