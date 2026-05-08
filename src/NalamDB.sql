use nalamDB;
create table users
(
    id       bigint auto_increment primary key,
    name     varchar(25),
    email    varchar(30),
    password varchar(15),
    role     enum ('PHARMACIST','CUSTOMER') not null default ('CUSTOMER')
);
select *
from users;
create table medicines
(
    medicineId   bigint auto_increment primary key,
    medicineName varchar(50) not null,
    description  varchar(50) not null,
    price        double      not null,
    expiryDate      bigint      not null,
    quantity     bigint      not null,
    pharmacistId bigint not null ,
    foreign key (pharmacistId) references users(id)
);
drop table medicines;

SELECT id FROM users WHERE email = 'Mani@gmail.co';
select * from medicines;

create table orders
(
    orderId      bigint primary key auto_increment not null ,
    userId       bigint not null ,
    medicineId   bigint not null ,
    medicineName varchar(50) not null ,
    price        double not null ,
    quantity     bigint not null,
    totalAmount double not null ,
    orderDate bigint not null ,
    foreign key (userId) references users (id),
    foreign key (medicineId) references medicines(medicineId)
);

drop table orders;