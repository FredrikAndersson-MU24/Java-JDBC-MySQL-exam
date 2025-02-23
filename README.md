# Java-JDBC-MySQL-exam

A text based library app written in Java. Connected to a MySQL database using JDBC.

## Prerequisites

### Creating the MySQL database
Before running the application, the MySQL database needs to be created. Use the script from MySQL.txt. This will preload 
the database with some books, authors, users and loans.

### Setting up IDE
Set up environmental variables for your database:
* MYSQL_URL - URL to the database. For example jdbc:mysql://localhost:3306/library
* MYSQL_USERNAME - Your server username
* MYSQL_PASSWORD - Your server password

## Using the application
To use the application the user needs to be registered. If they are registered they can choose to log in, 
otherwise they need to register. Please use the example users below.  
Admin user: `alicej`, `password1`  
Registered user: `charlieb`, `password3`

### Navigating
Use integers to pick alternatives from the menus. 0(zero) can be used to abort most if not all initiated input operations.
When ever an ID is requested an integer is expected. Error handled if not, message printed to the console.  
Other inputs allow strings to be input.

### Adding new users
From the Login menu the user can only register as a "RegisteredUser". Which means they can only read from the database,
lend books and return books.  
If an admin is logged in, they can add users as either RegisteredUser or AdminUser. Where the AdminUser does not have
the possibility to lend or return books, but they can add or remove books or authors from the database.
Username and password can not be the same. A new user can not have the same username as an existing user.

### Searching for books and authors
There several ways to find books in the library.
* List all available books - This method is available to the user
* List all books - This alternative is limited to the admins
* Search books by title - Find all titles that matches the entered search term. 

Authors can be found in two ways
* List all authors - Available only to the admins, this prints a list of all authors in the library.
* Search authors - This lets the user enter a query to search for.

### Lending and returning books
When the user lends a book, the book changes status to unavailable. A book that is unavailable cannot be lent.  
When a book has been returned, its status will be set to available and can once again be lent.
A user can review their activ loans. An admin can view both active loans as well as all loans, regardless of status. 

### Adding and deleting books and authors
If an author is deleted, their related books are also deleted, using cascading in the mySQL database.  
A book that is currently on loan can not be deleted.
