create schema hibernate_test; 
use hibernate_test;

create table Address (
  ID BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  CITY VARCHAR(50) not null,
  COUNTRY_ISO varchar(6) not null,
  STREET varchar(50) not null,
  POST_CODE varchar(10) not null
)ENGINE=InnoDB;

create table Person (
   ID BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT ,
   NAME varchar (30) not null, 
   AGE INT NOT NULL,
   LAST_NAME varchar (30) not null,
   ADDRESS_ID BIGINT not null, 
   constraint Person_Address_FK foreign key (ADDRESS_ID)
   references Address(ID) 
)ENGINE=InnoDB; 


create table Email (
    ID bigint not null primary key AUTO_INCREMENT, 
    URL varchar(32) not null , 
    OWNER_ID bigint not null ,
    CREATED_ON datetime not null,
    constraint owner_person_fk foreign key (OWNER_ID)
    references Person(id)
)ENGINE=InnoDB; 

create table Employee (
    ID bigint not null primary key, 
    DIVISION varchar(32) not null 
)ENGINE=InnoDB; 

create table PersonSingleTable(
   ID BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT ,
   NAME varchar (30) not null, 
   AGE INT NOT NULL,
   LAST_NAME varchar (30) not null,
   ADDRESS_ID BIGINT not null, 
   DIVISION varchar(32),
   Type varchar(8) not null,
   constraint PersonSingleTable_Address_FK foreign key (ADDRESS_ID)
   references Address(ID) 
)ENGINE=InnoDB; 

create table Project (
    ID bigint not null primary key AUTO_INCREMENT, 
    NAME varchar(32) not null, 
    CREATED_ON datetime not null
)ENGINE=InnoDB; 

create table Project_Employee (
    ID bigint not null primary key AUTO_INCREMENT, 
    EMPLOYEE_ID bigint ,
    PROJECT_ID bigint not null,
    constraint employee_fk foreign key (EMPLOYEE_ID)
    references Employee(ID),
    constraint project_fk foreign key (PROJECT_ID) 
    references Project(ID)
)ENGINE=InnoDB; 


create table Project_Employee_SingleTable (
    ID bigint not null primary key AUTO_INCREMENT, 
    EMPLOYEE_ID bigint ,
    PROJECT_ID bigint not null,
    constraint employeeSingleTable_fk foreign key (EMPLOYEE_ID)
    references PersonSingleTable(ID),
    constraint singletable_project_fk foreign key (PROJECT_ID) 
    references Project(ID)
)ENGINE=InnoDB; 


create table hibernate_sequences (
 sequence_name varchar(255), 
 sequence_next_hi_value integer 
)ENGINE=MyISAM; 


-- delete scripts 
-- use hibernate_test;

-- drop table address ;
-- drop table parent_child;
-- drop table parent;
-- drop table child;
-- drop table person ;

