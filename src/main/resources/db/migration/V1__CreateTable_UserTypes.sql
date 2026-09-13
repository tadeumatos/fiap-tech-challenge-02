CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

drop table if exists tb_usertypes;

create table tb_usertypes
(
    id uuid primary key,
    name varchar(50) not null,
    owner boolean
);

create index idx_tb_usertypes_name on tb_usertypes(name);