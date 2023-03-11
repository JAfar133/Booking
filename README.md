# Booking
booking project

## PostgreSQL

```SQL
create table person (
    id int8 not null generated by default as identity ,
    course integer,
    first_name varchar(255),
    institute varchar(255),
    last_name varchar(255),
    middle_name varchar(255),
    phone varchar(255),
    post varchar(255),
    structure varchar(255),
    primary key (id));

create table roomhall (
    id int8 not null generated by default as identity,
    address varchar(255),
    eng_name varchar(255),
    lat varchar(255),
    lon varchar(255),
    name varchar(255),
    primary key (id));

create table if not exists booking (
   id int8 not null generated by default as identity,
   booked_at timestamp,
   comment varchar(2048),
   date date,
   time_end time,
   time_start time,
   person_id int8 not null references person,
   roomhall_id int8 not null  references roomhall,
   primary key (id) );



insert into roomhall(address,name,eng_name,lat,lon) values (
               'ул. Гоголя, 14, Севастополь',
               'Зеркальный зал', 'zerkalniy-zal-gogolya',
               '44.597725', '33.519979'
           );
insert into roomhall(address,name,eng_name,lat,lon) values (
               'ул. Гоголя, 14, Севастополь',
               'Холл 2', 'hall2-gogolya',
               '44.597725', '33.519979'
           );
insert into roomhall(address,name,eng_name,lat,lon) values (
               'ул. Гоголя, 14, Севастополь',
               'Холл 3', 'hall3-gogolya',
               '44.597725', '33.519979'
           );
insert into roomhall(address,name,eng_name,lat,lon) values (
               'ул. Гоголя, 14, Севастополь',
               'Актовый зал', 'actoviy-zal-gogolya',
               '44.597725', '33.519979'
           );
insert into roomhall(address,name,eng_name,lat,lon) values (
               'ул. Курчатова, 7, Севастополь',
               'Актовый зал', 'actoviy-zal-kurchatova',
               '44.624367', '33.570572'
           );
```
