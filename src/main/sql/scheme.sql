-- 学生数据库
drop database if exists db_school;
create database db_school;

-- 学生表
drop table if exists db_school.student;
create table db_school.student
(
  id   int primary key auto_increment comment '主键',
  sno  varchar(255) not null unique comment '学号',
  name varchar(255) comment '姓名'
) comment '学生表';

insert into db_school.student
values (null, 's1950213', '张珊珊');
insert into db_school.student
values (null, 's1950214', '张强');
insert into db_school.student
values (null, 's1950215', '李强');
insert into db_school.student
values (null, 's1950216', '张小萌');

-- 课程表
drop table if exists db_school.course;
create table db_school.course
(
  id   int primary key auto_increment comment '主键',
  cno  varchar(255) not null unique comment '课程号',
  name varchar(255) comment '课程名'
) comment '课程表';

insert into db_school.course
values (null, 'c222225', '数据库');
insert into db_school.course
values (null, 'c222226', 'c语言');
insert into db_school.course
values (null, 'c222227', 'java');
insert into db_school.course
values (null, 'c222228', 'android');

-- 选课表
drop table if exists db_school.sc;
create table db_school.sc
(
  id  int primary key auto_increment comment '主键',
  sid int not null comment '学生id',
  cid int not null comment '课程id'
) comment '选课表';

-- 追加外键
alter table db_school.sc
  add constraint
    sc_fk_sid
    foreign key (sid)
      references db_school.student (id);
alter table db_school.sc
  add constraint
    sc_fk_cid
    foreign key (cid)
      references db_school.course (id);

insert into db_school.sc
values (null, 1, 1);
insert into db_school.sc
values (null, 1, 2);
insert into db_school.sc
values (null, 2, 1);
insert into db_school.sc
values (null, 3, 2);
insert into db_school.sc
values (null, 4, 4);
