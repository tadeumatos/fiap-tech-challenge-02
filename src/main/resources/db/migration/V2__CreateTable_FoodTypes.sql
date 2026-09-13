drop table if exists tb_foodtypes;

create table tb_foodtypes
(
    id uuid primary key,
    name varchar(50) not null
);

create index idx_tb_foodtypes_name on tb_foodtypes(name);