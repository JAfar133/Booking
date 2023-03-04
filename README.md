# Booking
booking project

## PostgreSQL

### booking
```SQL
create table public.booking
(
    id          integer generated by default as identity primary key,
    person_id   integer references public.person,
    booked_at   timestamp,
    roomhall_id integer not null references public.roomhall,
    date        date,
    time_start  time,
    time_end    time
);
```

### person
```SQL
create table public.person
(
    id          integer generated by default as identity primary key,
    post        varchar     not null,
    phone       varchar(12) not null,
    institute   varchar,
    course      integer,
    structure   varchar,
    first_name  varchar     not null,
    last_name   varchar     not null,
    middle_name varchar     not null
);
```
### roomhall
```SQL
create table public.roomhall
(
    id      integer generated by default as identity primary key,
    name    varchar not null,
    address varchar not null,
    lon     double precision,
    lat     double precision
);
```
