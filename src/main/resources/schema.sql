create table if not exists `user`
(
    id          char(19) not null primary key,
    account     varchar(12) not null unique,
    password    varchar(65) NOT NULL,
    department  json NOT NULL comment '{depId,name}',
    name        varchar(10) not null ,
    role         char(4),/*随机，防止猜到*/
    `group`     tinyint unsigned null,/*0-255*/

    student       json        null comment '{"teacherId", "teacherName", "queueNumber", "projectTitle"}',
    teacher       json         null comment '{"A", "C", "total"}',
    create_time datetime not null default current_timestamp,
    update_time datetime not null default current_timestamp on update current_timestamp,
    unique index (account),
    index ((cast(department->>'$.depId' as char(19)) collate utf8mb4_bin),role,`group`)
);

/*账号索引*/

create table if not exists `process`
(
    id          char(19) not null primary key,
    depId       char(19)not null ,
    name        varchar(20)not null,
    parts       json not null comment
        '[{"number":,"name:","weight:","description:"}]',
    weight      tinyint unsigned not null,
    add_part    json null comment '[{"number","name","description"}]',
    create_time datetime not null default current_timestamp,
    update_time datetime not null default current_timestamp on update current_timestamp,
    index(depId)
);
create table if not exists `score`
(
    id          char(19) not null primary key,
    student_id  char(19) not null,
    process_id  char(19) not null,
    `group`     tinyint unsigned null,
    teacher     json comment 'teacherId:,teacherName',
    process_name varchar(20)not null,
    detail      json comment '[{number:,name:,score:,weight:}]',
    index(student_id,process_id,(cast(teacher->>'$.teacherId' as char(19))collate utf8mb4_bin)),
    index(`group`,process_id,student_id)
);

create table if not exists `department`
(
    id          char(19) not null primary key,
    name        varchar(20)
);
