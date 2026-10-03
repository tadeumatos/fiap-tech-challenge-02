drop table if exists tb_usertypes;

create table tb_usertypes
(
    id uuid primary key,
    name varchar(50) not null,
    owner boolean,
    created_at timestamp,
    updated_at timestamp
);

create index idx_tb_usertypes_name on tb_usertypes(name);