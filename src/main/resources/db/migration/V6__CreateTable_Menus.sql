drop table if exists tb_menus;

create table tb_menus
(
    id uuid primary key,
    name varchar(50) not null,
    description varchar(100) null,
    price NUMERIC(10, 2) NOT NULL check(price>=0),
    only_local boolean,
    food_photo varchar(200) not null,
    restaurant_id uuid not null,
    active boolean,
    created_at timestamp,
    updated_at timestamp,
    constraint fk_tb_restaurants_id foreign key(restaurant_id)
        references tb_restaurants(id)
);

create index idx_tb_menus_restaurant_id on tb_menus(restaurant_id);