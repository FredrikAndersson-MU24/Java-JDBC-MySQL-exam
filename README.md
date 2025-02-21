# Java-JDBC-MySQL-exam

## A text based library app written in Java. Connected to a MySQL database using JDBC.

### Creating the MySQL database
Before running the application, the MySQL database needs to be created. Use the script from MySQL.txt. This will preload 
the database with some books, authors, users and loans.
Environmental variables: 
* MYSQL_URL
* MYSQL_USERNAME
* MYSQL_PASSWORD

### Running the Java application
To use the application the user needs to be registered. If they are registered they can choose to log in, 
otherwise they need to register.   
From the Login menu the user can only register as a "RegisteredUser". Which means they can only read from the database, 
lend books and return books. 
If an admin is logged in, they can add users as either RegisteredUser or AdminUser. Where the AdminUser does not have 
the possibility to lend or return books, but they can add or remove books or authors from the database.  
Example admin user: `alicej`, `password1`  
Example registered user: `charlieb`, `password3`

### Login
1. Login as registered user or admin
2. Register as user
0. Quit


### User menu
Once logged in, the user has several options.

1. Lend a book
2. Return a book
3. Check for active loans
4. List all available books
5. Search books by title
6. Search authors
0. Log out

### Admin menu
An admin is greeted with a different menu, they can add or delete books, add or delete authors.     

1. Add a book
2. Delete a book
3. List all books
4. Add author
5. List all authors
6. Add user
7. View all active loans
8. View all loans
0. Log out

