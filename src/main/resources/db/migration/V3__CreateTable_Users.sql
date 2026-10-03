drop table if exists tb_users;

create table tb_users
(
    id uuid primary key,
    name varchar(50) not null,
    email varchar(100) not null,
    usertype_id uuid not null,
    created_at timestamp,
    updated_at timestamp,
    constraint fk_tb_user_id foreign key(usertype_id)
        references tb_usertypes(id)
);
