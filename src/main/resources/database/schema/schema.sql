create table if not exists PUBLIC.RECORDS(
    CLEARTEXT CHARACTER VARYING(300),
    SALT      CHARACTER VARYING(200),
    ALGORITHM CHARACTER VARYING(200),
    ENCRYPT   CHARACTER VARYING(1000),
    ID        CHARACTER VARYING(64) default RANDOM_UUID() not null,
    constraint "RECORDS_pk"
        primary key (ID)
);

comment on table PUBLIC.RECORDS is '记录表';

comment on column PUBLIC.RECORDS.CLEARTEXT is '明文';

comment on column PUBLIC.RECORDS.SALT is '加密盐';

comment on column PUBLIC.RECORDS.ALGORITHM is '加密算法';

comment on column PUBLIC.RECORDS.ENCRYPT is '加密后的密文';

create table if not exists ERROR_LOG(
    id             varchar(64) default random_uuid() not null
        constraint ERROR_LOG_pk_2
            unique,
    error_data     BLOB                              not null,
    handler_status INTEGER     default 0             not null,
    create_time    DATETIME,
    constraint ERROR_LOG_pk
        primary key (id)
);

comment on table ERROR_LOG is '数据保存错误日志';

comment on column ERROR_LOG.id is '主键 id';

comment on column ERROR_LOG.error_data is '发生错误的数据(使用 json 格式)';

comment on column ERROR_LOG.handler_status is '数据处理状态(0:未处理,1:处理失败)';

comment on column ERROR_LOG.create_time is '创建时间';

create index ERROR_LOG_create_time_index
    on ERROR_LOG (create_time desc);
