CREATE TABLE Measurements (
    id serial primary key,
    temperature double precision not null,
    humidity double precision not null,
    timeStamp bigint not null,
    deviceId varchar(100) not null
);