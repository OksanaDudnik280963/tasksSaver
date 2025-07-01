drop table if exists TASKS;
create table TASKS (
                       TASK_COST decimal(38,2),
                       CREATED datetime(6) not null,
                       OPERATION_COUNT bigint,
                       TASK_ID bigint not null auto_increment,
                       UPDATED datetime(6) not null,
                       CHANGED_BY varchar(255) not null,
                       CREATED_BY varchar(255) not null,
                       TASK_DESCRIPTION varchar(255),
                       TASK_NAME varchar(255) not null,
                       TASK_STATUS varchar(255),
                       primary key (TASK_ID)
);