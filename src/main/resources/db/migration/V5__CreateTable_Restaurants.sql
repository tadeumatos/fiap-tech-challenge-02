drop table if exists tb_restaurants;

create table tb_restaurants
(
    id uuid primary key,
    name varchar(50) not null,
    description varchar(100),
    address_id uuid not null,
    address_number  varchar(10) not null,
    address_complement varchar(20),
    owner_id uuid not null,
    foodtype_id uuid not null,
    start_time time not null,
    end_time time not null,
    created_at timestamp,
    updated_at timestamp,
    constraint fk_tb_users_id foreign key(owner_id)
        references tb_users(id),
    constraint fk_tb_foodtypes_id foreign key(foodtype_id)
        references tb_foodtypes(id),
    constraint fk_tb_addresses_id foreign key(address_id)
        references tb_addresses(id)

);

create index idx_tb_restaurants_foodtype_id on tb_restaurants(foodtype_id);