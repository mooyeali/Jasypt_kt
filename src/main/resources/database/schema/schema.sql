create table if not exists records (
    cleartext  varchar(300) ,
    salt varchar(200),
    algorithm varchar(200),
    encrypt varchar(1000)
);
comment on table records is '记录表';
comment on column records.cleartext is '明文';
comment on column records.salt is '加密盐';
comment on column records.algorithm is '加密算法';
comment on column records.encrypt is '加密后的密文';

