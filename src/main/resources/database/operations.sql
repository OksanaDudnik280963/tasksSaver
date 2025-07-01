drop table if exists OPERATIONS;
create table OPERATIONS (
                            OPERATION_PRICE decimal(38,2) not null,
                            CREATED datetime(6) not null,
                            OPERATION_ID bigint not null auto_increment,
                            UPDATED datetime(6) not null,
                            CHANGED_BY varchar(255) not null,
                            CREATED_BY varchar(255) not null,
                            OPERATION_DESCRIPTION varchar(255),
                            OPERATION_NAME varchar(255) not null,
                            OPERATION_STATUS varchar(255),
                            TASK_NAME varchar(255) not null,
                            primary key (OPERATION_ID)
);