drop table Borrower;
create table Borrower
	(bid char(20) not null,
	password varchar(20) null,
	name char(40) null,
	address varchar(40) null,
	phone char(12) null,
	emailAddress varchar(40) null,
	sinOrStNo char(20) null,
	expiryDate char(2) null,
	btype char(10) null);
	
drop table BorrowerType;
create table BorrowerType
	(type type char(10) not null,
	bookTimeLimit int null);
	
drop table Book;
create table Book
	(callNumber varchar(40) not null,
	isbn number(10) null,
	title varchar(40) null,
	mainAuthor char(40) null,
	publisher char(40) null,
	publicationYear year not null);
	
drop table HasAuthor;
create table HasAuthor
	(callNumber varchar(40) not null,
	name char(40) not null);
	
drop table HasSubject;
create table HasSubject
	(callNumber int not null,
	subject char(20) not null);

drop table BookCopy;
create table BookCopy
	(callNumber int not null,
	copyNo int not null,
	status char(10) null);
	
drop table HoldRequest;
create table HoldRequest
	(hid int not null,
	bid int null,
	callNumber int null,
	issuedDate date null);

drop table fine;
CREATE TABLE fine(
        fid int not null,
        amount number(10) not null,
        issuedDate varChar(20) not null,
        paidDate varChar(20) null,
        boridId char(8) null,
        );

drop table borrowing;
CREATE TABLE borrowing(
        borid char(8) not null,
        bid char(8) not null,
        callNumber varChar(20) null,
        copyNo varChar(20) null,
        outDate varChar(20) null,
        inDate varChar(20) null);
		
		
 
insert into Book
values('KF8840.F72.1999','0123456789', 'how 2 be a chill bro',
'Dr. Mike Wu', 'Penguin', '1999');

insert into BookCopy
values('KF8840.F72.1999','1', 'reserved');

insert into HasAuthor
values('KF8840.F72.1999','Dr. Mike Wu');
		
		
