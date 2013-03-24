drop table Borrower;
create table Borrower
	(bid int not null primary key,
	password varchar(20) null,
	name char(40) null,
	address varchar(40) null,
	phone char(12) null,
	emailAddress varchar(40) null,
	sinOrStNo char(20) null,
	expiryDate char(20) null,
	btype char(10) null);
	
drop sequence bid_incr;
CREATE SEQUENCE bid_incr
	START WITH 1
	INCREMENT BY 1;

CREATE OR REPLACE TRIGGER bid_incr
	BEFORE INSERT
	ON Borrower
	REFERENCING NEW AS NEW
	FOR EACH ROW
	BEGIN
	SELECT bid_incr.nextval INTO :NEW.bid FROM dual;
	END;
	/
	
drop table BorrowerType;
create table BorrowerType
	(type char(10) not null primary key,
	bookTimeLimit int null);
	
drop table Book;
create table Book
	(callNumber varchar(40) not null primary key,
	isbn varchar(10) null,
	title varchar(40) null,
	mainAuthor char(40) null,
	publisher char(40) null,
	publicationYear varchar(10) not null);
	
drop table HasAuthor;
create table HasAuthor
	(callNumber varchar(40) not null primary key,
	name char(40) not null primary key);
	
drop table HasSubject;
create table HasSubject
	(callNumber varchar(40) not null primary key,
	subject char(20) not null primary key);

drop table BookCopy;
create table BookCopy
	(callNumber varchar(40) not null primary key,
	copyNo int not null primary key,
	status char(10) null);
	
drop table HoldRequest;
create table HoldRequest
	(hid int not null primary key,
	bid int null,
	callNumber int null,
	issuedDate date null);
	
drop sequence hold_incr;
CREATE SEQUENCE hold_incr
	START WITH 1
	INCREMENT BY 1;

CREATE OR REPLACE TRIGGER hold_incr
	BEFORE INSERT
	ON HoldRequest
	REFERENCING NEW AS NEW
	FOR EACH ROW
	BEGIN
	SELECT hold_incr.nextval INTO :NEW.hid FROM dual;
	END;
	/

drop table fine;
CREATE TABLE fine(
        fid int not null primary key,
        amount number(10) not null,
        issuedDate varChar(20) not null,
        paidDate varChar(20) null,
        boridId int null);
		
drop sequence fid_incr;
CREATE SEQUENCE fid_incr
	START WITH 1
	INCREMENT BY 1;

CREATE OR REPLACE TRIGGER fid_incr
	BEFORE INSERT
	ON fine
	REFERENCING NEW AS NEW
	FOR EACH ROW
	BEGIN
	SELECT fid_incr.nextval INTO :NEW.fid FROM dual;
	END;
	/

drop table borrowing;
CREATE TABLE borrowing(
        borid int not null primary key,
        bid char(8) not null,
        callNumber varChar(20) null,
        copyNo varChar(20) null,
        outDate varChar(20) null,
        inDate varChar(20) null);
		
drop sequence bor_incr;
CREATE SEQUENCE bor_incr
	START WITH 1
	INCREMENT BY 1;

CREATE OR REPLACE TRIGGER bor_incr
	BEFORE INSERT
	ON borrowing
	REFERENCING NEW AS NEW
	FOR EACH ROW
	BEGIN
	SELECT bor_incr.nextval INTO :NEW.borid FROM dual;
	END;
	/
		
insert into Book
values('KF8840.F72.1999','0123456789', 'how 2 be a chill bro',
'Dr. Mike Wu', 'Penguin', '1999');

insert into BookCopy
values('KF8840.F72.1999',1, 'reserved');

insert into HasAuthor
values('KF8840.F72.1999','Dr. Mike Wu');
		
		
