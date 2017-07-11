--------------------------------------------------------
--  DDL for Table CITY
--------------------------------------------------------

  CREATE TABLE "TIGER"."CITY" 
   (	"CT_ID" NUMBER(*,0), 
	"ST_ID" NUMBER(*,0), 
	"CT_NAME" VARCHAR2(50 BYTE)
   ) ;

Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (3,4,'Ajmer');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (4,4,'Alwar');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (5,4,'Bali');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (6,4,'Bara');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (7,4,'Barmer');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (8,4,'Beawar');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (9,4,'Bharatpur');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (10,4,'Bhilwara');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (11,4,'Bijainagar');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (12,4,'Bikaner');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (13,4,'Bilara');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (14,4,'Bundi');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (15,4,'Dariba');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (16,4,'Dausa');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (17,4,'Didwana');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (18,4,'Gandhigram');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (19,4,'Ganganagar');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (20,4,'Jaipur');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (21,4,'Jaipur City');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (22,4,'Jhalawar');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (23,4,'Jhalrapata');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (24,4,'Jhunjhunu');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (25,4,'Jodhpur');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (26,4,'Khetri');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (27,4,'Kota');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (28,4,'Kuala');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (29,4,'Kuchama');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (30,4,'Lalsot');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (31,4,'Lamba');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (32,4,'Mandi');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (33,4,'Meghana');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (34,4,'Nagar');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (35,4,'Nagaur');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (36,4,'Naraina');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (37,4,'Pali');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (38,4,'Pali-marwar');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (39,4,'Pilani');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (40,4,'Rawatbhata');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (41,4,'Sena');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (42,4,'Sikar');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (43,4,'Sri Ganganagar');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (44,4,'Surana');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (45,4,'Tijara');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (46,4,'Tonk');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (47,4,'Udaipur');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (48,33,'Delhi');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (50,33,'Mumbai');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (51,33,'Bangalore');
Insert into TIGER.CITY (CT_ID,ST_ID,CT_NAME) values (49,33,'Chennai');
--------------------------------------------------------
--  DDL for Table COUNTRY
--------------------------------------------------------

  CREATE TABLE "TIGER"."COUNTRY" 
   (	"CTRY_ID" NUMBER(*,0), 
	"CTRY_NAME" VARCHAR2(50 BYTE)
   ) ;
Insert into TIGER.COUNTRY (CTRY_ID,CTRY_NAME) values (1,'INDIA');
Insert into TIGER.COUNTRY (CTRY_ID,CTRY_NAME) values (2,'USA');
--------------------------------------------------------
--  DDL for Table GARAGES
--------------------------------------------------------

  CREATE TABLE "TIGER"."GARAGES" 
   (	"C_ID" NUMBER, 
	"BUILDING_NAME" VARCHAR2(100 BYTE), 
	"ISPRIVATE" VARCHAR2(100 BYTE) DEFAULT NULL, 
	"ADDRESS" VARCHAR2(100 BYTE) DEFAULT NULL, 
	"G_ID" NUMBER
   );
   Insert into TIGER.GARAGES (C_ID,BUILDING_NAME,ISPRIVATE,ADDRESS,G_ID) values (49,'Forum Mall','Public','Vadapalani',1);
Insert into TIGER.GARAGES (C_ID,BUILDING_NAME,ISPRIVATE,ADDRESS,G_ID) values (49,'DLF','Private','Porur',2);
Insert into TIGER.GARAGES (C_ID,BUILDING_NAME,ISPRIVATE,ADDRESS,G_ID) values (49,'Phoneix','Private','Velachery',3);
Insert into TIGER.GARAGES (C_ID,BUILDING_NAME,ISPRIVATE,ADDRESS,G_ID) values (49,'HP','Private','Porur',4);
Insert into TIGER.GARAGES (C_ID,BUILDING_NAME,ISPRIVATE,ADDRESS,G_ID) values (49,'Park','Public','Porur',5);
--------------------------------------------------------
--  DDL for Table STATE
--------------------------------------------------------

  CREATE TABLE "TIGER"."STATE" 
   (	"ST_ID" NUMBER(*,0), 
	"ST_NAME" VARCHAR2(50 BYTE), 
	"CTRY_ID" NUMBER(*,0)
   );
   Insert into TIGER.STATE (ST_ID,ST_NAME,CTRY_ID) values (4,'Rajasthan',1);
Insert into TIGER.STATE (ST_ID,ST_NAME,CTRY_ID) values (5,'Andaman and Nicobar Islands',1);
Insert into TIGER.STATE (ST_ID,ST_NAME,CTRY_ID) values (6,'Andhra Pradesh',1);
Insert into TIGER.STATE (ST_ID,ST_NAME,CTRY_ID) values (7,'Arunachal Pradesh',1);
Insert into TIGER.STATE (ST_ID,ST_NAME,CTRY_ID) values (8,'Assam',1);
Insert into TIGER.STATE (ST_ID,ST_NAME,CTRY_ID) values (9,'Bihar',1);
Insert into TIGER.STATE (ST_ID,ST_NAME,CTRY_ID) values (10,'Chandigarh',1);
Insert into TIGER.STATE (ST_ID,ST_NAME,CTRY_ID) values (11,'Chhattisgarh',1);
Insert into TIGER.STATE (ST_ID,ST_NAME,CTRY_ID) values (12,'Dadra and Nagar Haveli',1);
Insert into TIGER.STATE (ST_ID,ST_NAME,CTRY_ID) values (13,'Daman and Diu',1);
Insert into TIGER.STATE (ST_ID,ST_NAME,CTRY_ID) values (14,'Delhi',1);
Insert into TIGER.STATE (ST_ID,ST_NAME,CTRY_ID) values (15,'Goa',1);
Insert into TIGER.STATE (ST_ID,ST_NAME,CTRY_ID) values (16,'Gujarat',1);
Insert into TIGER.STATE (ST_ID,ST_NAME,CTRY_ID) values (17,'Haryana',1);
Insert into TIGER.STATE (ST_ID,ST_NAME,CTRY_ID) values (18,'Himachal Pradesh',1);
Insert into TIGER.STATE (ST_ID,ST_NAME,CTRY_ID) values (19,'Jammu and Kashmir',1);
Insert into TIGER.STATE (ST_ID,ST_NAME,CTRY_ID) values (20,'Jharkhand',1);
Insert into TIGER.STATE (ST_ID,ST_NAME,CTRY_ID) values (21,'Karnataka',1);
Insert into TIGER.STATE (ST_ID,ST_NAME,CTRY_ID) values (22,'Kerala',1);
Insert into TIGER.STATE (ST_ID,ST_NAME,CTRY_ID) values (23,'Madhya Pradesh',1);
Insert into TIGER.STATE (ST_ID,ST_NAME,CTRY_ID) values (24,'Maharashtra',1);
Insert into TIGER.STATE (ST_ID,ST_NAME,CTRY_ID) values (25,'Manipur',1);
Insert into TIGER.STATE (ST_ID,ST_NAME,CTRY_ID) values (26,'Meghalaya',1);
Insert into TIGER.STATE (ST_ID,ST_NAME,CTRY_ID) values (27,'Mizoram',1);
Insert into TIGER.STATE (ST_ID,ST_NAME,CTRY_ID) values (28,'Nagaland',1);
Insert into TIGER.STATE (ST_ID,ST_NAME,CTRY_ID) values (29,'Orissa',1);
Insert into TIGER.STATE (ST_ID,ST_NAME,CTRY_ID) values (30,'Puducherry',1);
Insert into TIGER.STATE (ST_ID,ST_NAME,CTRY_ID) values (31,'Punjab',1);
Insert into TIGER.STATE (ST_ID,ST_NAME,CTRY_ID) values (32,'Sikkim',1);
Insert into TIGER.STATE (ST_ID,ST_NAME,CTRY_ID) values (33,'Tamil Nadu',1);
Insert into TIGER.STATE (ST_ID,ST_NAME,CTRY_ID) values (34,'Tripura',1);
Insert into TIGER.STATE (ST_ID,ST_NAME,CTRY_ID) values (35,'Uttar Pradesh',1);
Insert into TIGER.STATE (ST_ID,ST_NAME,CTRY_ID) values (36,'Uttarakhand',1);
Insert into TIGER.STATE (ST_ID,ST_NAME,CTRY_ID) values (37,'West Bengal',1);
--------------------------------------------------------
--  DDL for Table PARKING_AREA
--------------------------------------------------------

  CREATE TABLE "TIGER"."PARKING_AREA" 
   (	"G_ID" NUMBER, 
	"P_ID" NUMBER, 
	"P_NAME" VARCHAR2(20 BYTE), 
	"IS_AVAILABLE" VARCHAR2(20 BYTE)
   );

Insert into TIGER.PARKING_AREA (G_ID,P_ID,P_NAME,IS_AVAILABLE) values (2,1,'A Block','5');
Insert into TIGER.PARKING_AREA (G_ID,P_ID,P_NAME,IS_AVAILABLE) values (2,2,'B Block','5');
Insert into TIGER.PARKING_AREA (G_ID,P_ID,P_NAME,IS_AVAILABLE) values (2,3,'C Block','5'); 
--------------------------------------------------------
--  DDL for Table PARKING_BOOKINGS
--------------------------------------------------------

  CREATE TABLE "TIGER"."PARKING_BOOKINGS" 
   (	"P_ID" NUMBER, 
	"CAR_REGNO" NUMBER, 
	"IN_TIME" DATE, 
	"OUT_TIME" DATE, 
	"CAR_TYPE" VARCHAR2(20 BYTE)
   ) ;
   
   ---------------------------------------------------------------------------