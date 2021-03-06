## mysql复习与深入理解
### 示例代码 [learn.sql](https://github.com/ActStrady/review-mysql/blob/master/src/main/sql/learn.sql)
- #### 约束(constraint)
    - 约束可以在表内进行定义，也可以在创建完表之后进行追加（修改表）
    其主要的语法格式是`constraint 键名 键类型 (字段)`
    1. 主键约束PRIMARY KEY
        - 表示非空和唯一， 一般直接在字段后面加primary key
        - 复合主键写法：在表内定义后加`primary key(字段1, 字段2)`
        也可以使用约束的写法`constraint 键名 primary key(字段1, 字段2)`
    2. 外键约束
        - 表示一个字段只能来自另一个表中的字段,外键名有一定的规范便于以后删除，一般是：`从表名_fk_列名`
        - 写法：可以在表内定义也可以在表外追加，一般要在表外追加
        
        表内定义示例
        ```
           constraint student_fk_departmentId
               foreign key (departmentId)
                 references db_school.department (id);
        ```
        表外追加
        ```mysql
         alter table db_school.student
           add constraint
             student_fk_departmentId
             foreign key (departmentId)
               references db_school.department (id);
        ```
    3. 唯一约束unique
        - 不能重复但可以为空
        - 一般直接在建表时 直接字段后加unique
        - 也可以追加方式
    4. 非空约束 not null
        - 表示不可以为空值
        - 直接在建表时字段后加not null
- #### 常用的几个语句
    1. default 
       - 用来给字段设置一个默认值
       - 直接在建表时字段后加default 默认值
    2. Auto Increment(自增)
        - 一般用来给主键设置自增
        - 直接在建表时字段后加auto increment = 数值， 可以设置自增开始数值
    3. 级联操作（删除，更新， 置空）
        - 当定义了主外键关系时当我们删除主表的数据时，当从表对这条数据有引用时，这时无法删除主表数据，必须先删掉从表的数据
        - 当使用级联删除时就可以删除会将从表的数据也一起删除，当使用级联置空时会将从表中对应的外键内容置为null，主表内容会删除。
        - 同样道理，级联就是更新主表时自动将从表数据自动更新
        - 在定义外键时在references后添加下面内容
       ```
        on delete cascade -- 级联删除
        on update cascade -- 级联更新
        on delete set null-- 级联置空
       ```
    4. 暂时禁用MySQL中的外键约束
       - 当对有外键关系的表删除时会提示有外键关系不可删除，只能删除了主表才能删除从表。可以先将外键约束禁用，只要不修改外键，禁用完开启后外键关系还在
       - 使用下边的命令来进行关闭和开启
       ```mysql
         -- 关闭
         set foreign_key_checks = 0;
         -- 开启
         set foreign_key_checks = 1;
       ```
    5. binary 区分大小写
        - 加在关键字前表示区分大小写一般用的很少
    6. distinct 去除重复
        - 放在 select 的字段前
    7. ifnull(？,？)
        - ifnull()函数 不为null的时候取前一个值，为null取后一个
- #### DQL（Data Query Language）语句
     1. show语句
        - show table status from database_name; 显示库中所有表的信息
        - show table status where name = ‘table_name’; 显示表的信息
        - show full columns from table_name;显示表的所有列信息（包含comment信息），当不加full时与desc的作用相同
        - show full tables from database_name; 显示库中的表和视图
        - show variables; 显示变量
        - show create table(view) table_name; 显示建表(视图)语句
        - show index from table_name; 显示表中索引信息
     2. 分页实现（limit offset）
        - limit 表示几条
        - offset 表示从哪开始起始于0
     3. 通配符 escape
        - escape 允许确定一个转义字符，告诉DBMS紧跟在转义字符之后的字符看作是实际值
        - 一个例子`like '%m%' escape ‘m'` “%M%”中的第二个百分符（%）作为实际值，而不是通配符。
     4. 多表连接查询
        - 内连接 (inner)join on inner可以省略，查询到的是左右表同时有的数据
        - 外连接
            - 左外连接(outer) left join on outer可以省略，查到的是符合条件左表为主的值，即使是右表数据为空
                - 一个极端的例子是右表是空表，查到的数据右表的列都是null值
            - 右外连接(outer) right join on outer可以省略,与左外连接意思一样
            - #### 对于左右外连接，因为一般连接的表是带有外键关系的表，所以当左表是从表时，左连接查询多出来的是 从表外键为空的行，右连接查询多出来的是 从表根本没有的外键（主表有的主键而从表根本没有）
            - 拿学生表和系别表来说，学生表有一个外键（系别id
        ）指向系别表，当以学生表为左表的时候，左连接查询时会多出几条学生表外键为空的数据，右连接查询时会多出几条学生表中根本没有的系别这时候学生数据都为空。示例见learn.sql
        - mysql没有全连接 可以使用union来实现
            - 就是使用union来连接左右连接来实现
        ### 注：当on后的条件两个值一样时，可以使用using(值)来代替on，但一般用的少。
        
- #### DTL(事务)
    - 事务用在DML语句insert update delete,DQL和事务无关
    - DDL（创建语句）会隐式提交事务
    - 开启事务`start transaction;`
    - 设置保留点`savepoint point-name`
    - 显式提交事务的方式，回滚或者提交
    - 回滚到保留点`rollback to point-name`
    - 提交 `commit`
    
    - #### 实际上一般事务是用来防止删除更新出错
    - 还有设置不自动提交`set autocommit = 0`
- #### 常用函数
    - abs 绝对值
    - bin 转为二进制
    - pi π的值
    - trim 去除两端空格 rtrim 右空格 ltrim左空格
    - lower 转换为小写
    - current_date 当前日期
    - now 现在的时间
    - date_add 指定时间加上间隔时间`date_add(now(), interval 1 year)`
    - dayofweek(date) 返回 date 所代表的一星期中的第几天(1~7)
    - week(date) 返回日期 date 为一年中第几周(0~53)
    - year(date) 返回日期 date 的年份(1000~9999)
    - inet_aton 用来把ip地址转换为数值
    - inet_ntoa 用来把数值转化为ip
    - #### having 和 group by
        - group by 一般是用在有函数列的查询，表示分组查询，having是组检索，是检索分组的内容
- #### sql 导入数据和备份数据
    - 导入
        - `source file_name.sql` 在终端输入
        - load    sql语句，使用切割符切后存入
        ```
        load data local infile 'path_to_your_csv_file'
        into table your_db.your_table
        fields terminated by ',' (column_name1, column_name2, ...)
        set id = null;
        ```
    - 备份 在mysql的bin目录下执行
        - `mysqldump -B -u your_mysql_username -p database_name > file_name.sql`
- #### sql 视图
    - 视图就是被存储的查询
    - 语法
    ```
    create or replace view view_name as
    select column_name
    from table_name
    where condition;
    ```
    - 查询视图
      `SHOW FULL TABLES IN database_name WHERE TABLE_TYPE LIKE 'VIEW';`
    - updatable view 简单查询 生成的，可以修改基表的数据
    - read-only view 复杂查询 生成的，不可以修改基表的数据
- 索引 index
    - 索引用来提高查询效率，但带来的问题就是索引也要占据内存，非必要的列不建议加索引
    - mysql的主键和外键(仅仅在innodb下)和唯一约束默认为索引
    - 语法
        ```
        # 查看索引
        show index from db_ip.ip_range;
        
        # 创建索引
        create index idx_min on db_ip.ip_range(min);
        show index from db_ip.ip_range;
        
        # 删除索引
        drop index idx_min on db_ip.ip_range;
        show index from db_ip.ip_range;
        ```
    
