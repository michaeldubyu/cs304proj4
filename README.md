MDMJ Library Systems Manual

PREFACE

The usage of this system is largely self explantory with respect to the project specifications given. However, we will visit basic functions and explain functionality in this document, as well as error handling and odd cases that may arise.

SECTION 1 - Clerk Actions

- Add a new borrower to the library. The user should  provide all the required information. There are a number of checks here, including restrictions on password length and that it must be constructed with combinations of alpha and numeric characters. Appropriate error messages are displayed for invalid types not conforming to data required. The borrower ID is returned if the record is created in the database.
- Check-out items borrowed by a borrower. The user provides their borrowing ID, call number and copy no of the book they wish to check out. Errors are given on attempts to check out books that do not exist. A transaction ID is given if the attempt was successful. 
- Processes a return.  If the item is overdue, a fine is assessed for the borrower.  If there is a hold request for this item by another borrower, the item is registered as "on hold" and a message is send to the borrower who made the hold request.
- Checks overdue items. The system displays a list of the items that are overdue and the borrowers who have checked them out. The email is provided as with the phone number, so they may choose their method of contacting the user.

SECTION 2 - Borrower Actions

- Search for books using keyword search on titles, authors and subjects. The result is a list of books that match the search together with the number of copies that are in and out. If there are no results, the user is informed.
- Check his/her account. The system will display the items the borrower has currently borrowed and not yet returned, any outstanding fines and the hold requests that have been placed by the borrower. If the ID supplied is invalid, the user is notified.
- Place a hold request for a book that is out. When the item is returned, the system sends an email to the borrower. 
- Pay a fine. If the user has no fines, they are alerted of this. If they have fines, it is displayed with the issued date, amount, and a field for them to edit with the date they have paid. After they have edited the field, the fine is considered paid.

SECTION 3 - Librarian Actions

- Adds a new book or new copy of an existing book to the library. The librarian provides the information for the new book, and the system adds it to the library. All fields must be filled out Since regex for callnumbers is extremely difficult, the burden is on the library to get this correct. The system makes sure that the ISBN code is a legimate 10-digit ISBN number. 
- Generate a report with all the books that have been checked out. For each book the report shows the callnumber, title, copy number, the date it was checked out and the due date. The system flags the items that are overdue. The items are ordered by the book call number.  If a subject is provided the report lists only books related to that subject, otherwise all the books that are out are listed by the report.
- Generate a report with the most popular items in a given year.  The librarian provides a year and a number n. University Library was founded in 1970, so n must be at least 1970. It is just a coincidence that this is the same year that unix time started. The system lists out the top n books that where borrowed the most times during that year. The books are ordered by the number of times they were borrowed.
