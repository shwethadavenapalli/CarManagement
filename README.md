# CarManagement

A Simple spring boot application which performs CRUD operations for CAR,DRIVER entities.

This application exposes endpoints for Driver to Select a Car

 ```
POST -  /associateCarToDriver/{CAR-LICENSE-PLATE-NUMBER}/{DRIVER-LICENSE-NuMBER}
PUT -  /disAssociateCarToDriver/{CAR-LICENSE-PLATE-NUMBER}/{DRIVER-LICENSE-NuMBER}

POST - /cars
sample json request body :  {
	"licensePlate":"CAR2",
	"seatCount":2,
	"engineType":"DIESEL"
}

POST - /drivers
sample json request body:{
	"name":"john",
	"address":"Mumbai",
	"phoneNumber":"123456890",
	"licenseNumber":"DL2"
}


 ```


