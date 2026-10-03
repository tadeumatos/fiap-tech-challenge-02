drop table if exists tb_addresses;

create table tb_addresses
(
    id uuid primary key,
    name varchar(50) not null,
    neighborhood varchar(50) not null,
    city varchar(30) not null,
    state varchar(30) not null,
    postal_code varchar(15) not null,
    country varchar(30) not null,
    created_at timestamp,
    updated_at timestamp
);