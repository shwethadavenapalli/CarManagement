
delete from car;

delete from driver_car;



INSERT INTO car(ID, license_plate,seat_count,engine_type,Status,DATE_CREATED,DATE_UPDATED)
VALUES(1, 'CARPLATE1', 2,  'DIESEL', 'ACTIVE',now(),now());


delete from driver;

insert into driver (id, name,address,phone_Number,license_Number,status,CREATED_DATE,DELETED,ONLINE_STATUS)
 values (1,'SHWETHA','MUMBAI','1234567890','LICENSE1','ACTIVE',now(),false,'ONLINE');


