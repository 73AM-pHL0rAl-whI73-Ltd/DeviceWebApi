/* old measurements table v.1 */
CREATE TABLE Measurements (
    id serial primary key,
    temperature double precision not null,
    humidity double precision not null,
    timeStamp bigint not null,
    deviceId varchar(100) not null
);

/* new tables database v.2 */
CREATE TABLE "DeviceInfo" (
                              "id" serial primary key,
                              "deviceId" UUID,
                              "deviceAlias" varchar(100),
                              "macAddress" varchar(14)
);

CREATE TABLE "SensorTypes" (
                               "id" serial primary key,
                               "sensorType" varchar(65)
);

CREATE TABLE "Devices" (
                           "id" serial primary key,
                           "sensorType" int references "SensorTypes",
                           "deviceInfoId" int references "DeviceInfo"
);

CREATE TABLE "DhtMessages" (
                               "id" serial primary key,
                               "temperature" double precision,
                               "humidity" double precision,
                               "timeStamp" bigint,
                               "deviceId" int references "Devices"
);