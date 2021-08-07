
CREATE DATABASE spring_batch
   WITH OWNER 'user'
   TEMPLATE template0
   ENCODING 'UTF-8'
   TABLESPACE  pg_default
   LC_COLLATE  'C'
   LC_CTYPE  'C'
   CONNECTION LIMIT  -1;

CREATE DATABASE data_migration
   WITH OWNER 'user'
   TEMPLATE template0
   ENCODING 'UTF-8'
   TABLESPACE  pg_default
   LC_COLLATE  'C'
   LC_CTYPE  'C'
   CONNECTION LIMIT  -1;

\c data_migration;

CREATE TABLE employee (
	empid int8 NOT NULL,
	halfofjoining varchar(255) NULL,
	monthofjoining int8 NULL,
	ageincompany float8 NULL,
	ageinyears float8 NULL,
	city varchar(255) NULL,
	country varchar(255) NULL,
	dateofbirth date NULL,
	dateofjoining date NULL,
	dayofjoining int8 NULL,
	dowofjoining varchar(255) NULL,
	email varchar(255) NULL,
	fathersname varchar(255) NULL,
	firstname varchar(255) NULL,
	gender varchar(255) NULL,
	lastname varchar(255) NULL,
	lastpercenthike varchar(255) NULL,
	middleinitial varchar(255) NULL,
	monthnameofjoining varchar(255) NULL,
	mothermaidenname varchar(255) NULL,
	mothersname varchar(255) NULL,
	nameprefix varchar(255) NULL,
	passwordencoded varchar(255) NULL,
	phonenumber varchar(255) NULL,
	placename varchar(255) NULL,
	quarterofjoining varchar(255) NULL,
	region varchar(255) NULL,
	salary float8 NULL,
	shortdow varchar(255) NULL,
	shortmonth varchar(255) NULL,
	ssn varchar(255) NULL,
	state varchar(255) NULL,
	timeofbirt time NULL,
	username varchar(255) NULL,
	weightinkgs float8 NULL,
	yearofjoining int8 NULL,
	zip varchar(255) NULL
);

