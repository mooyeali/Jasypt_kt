create table if not exists PUBLIC.RECORDS
(
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

