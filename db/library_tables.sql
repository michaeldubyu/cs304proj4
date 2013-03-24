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
	(callNumber varchar(40) not null,
	name char(40) not null,
    constraint HasAuthor_pk primary key (callNumber, name)
);
	
drop table HasSubject;
create table HasSubject
	(callNumber varchar(40) not null,
	subject char(20) not null,
    constraint HasSubject_pk primary key (callNumber, subject)
);

drop table BookCopy;
create table BookCopy
	(callNumber varchar(40) not null,
	copyNo int not null,
	status char(10) null,
    constraint BookCopy_pk primary key (callNumber, copyNo)
);
	
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

drop table Fine;
CREATE TABLE Fine(
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
	ON Fine
	REFERENCING NEW AS NEW
	FOR EACH ROW
	BEGIN
	SELECT fid_incr.nextval INTO :NEW.fid FROM dual;
	END;
	/

drop table Borrowing;
CREATE TABLE Borrowing(
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
	ON Borrowing
	REFERENCING NEW AS NEW
	FOR EACH ROW
	BEGIN
	SELECT bor_incr.nextval INTO :NEW.borid FROM dual;
	END;
	/
		
		
		
