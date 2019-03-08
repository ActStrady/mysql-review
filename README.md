## mysql难点要点总结
 - [约束(constraint)](https://github.com/ActStrady/review-mysql/blob/master/src/main/sql/scheme.sql)
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
  - 常用的几个语句
    1. default 
       - 用来给字段设置一个默认值
       - 直接在建表时字段后加default 默认值
    2. Auto Increment(自增)
        - 一般用来给主键设置自增
        - 直接在建表时字段后加auto increment = 数值， 可以设置自增开始数值
    3. 级联操作（删除，更新， 置空）
        - 当定义了主外键关系时当我们删除主表的数据时，当从表对这条数据有引用时，这时无法删除主表数据，必须先删掉从表的数据，
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