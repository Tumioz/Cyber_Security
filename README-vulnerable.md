# SQL Injection Vulnerability Demonstration (Vulnerable App)

## Overview
This application demonstrates how improper handling of user input in database queries leads to SQL Injection (SQLi). By directly concatenating user input into a SQL statement, the application allows an attacker to alter the logic of the query. This specific demonstration showcases an authentication bypass, where an attacker can gain access to the system without knowing a valid password.

## Project Structure
```text
school-project/
├── pom.xml                     # Maven configuration and SQLite JDBC dependency
├── school_project.db           # SQLite database file (auto-generated on run)
└── src/
    └── main/
        └── java/
            └── VulnerableApp.java  # The vulnerable CLI application
            
```

```How to Run
Compile the Project:
Ensure you have Maven installed. Open your terminal and run:

Bash
mvn clean compile



Execute the Application:
Run the compiled class using Java, ensuring the SQLite JDBC driver is on the classpath.

Bash
mvn exec:java -Dexec.mainClass="VulnerableApp"
```

Demonstration Steps
1. Expected Behavior (Legitimate Login)
When prompted, use the standard credentials to see how the application is intended to work.

Username: adam

Password: SuperSecret123!

Result: The system will output "Login Successful!" and display the user's details.

2. The Attack (Authentication Bypass)
Run the application again to demonstrate the vulnerability.

Username: admin' OR '1'='1

Password: (Leave blank or type random characters like bypass)

Result: The system logs you in successfully despite providing an incorrect password.


Why the Attack Works
The application executes the following query:

SQL
SELECT * FROM users WHERE username = 'admin' OR '1'='1' AND password = 'bypass'
Because '1'='1' evaluates to TRUE, the database ignores the password requirement, returning the admin user record and bypassing the authentication mechanism completely.

Author: 𝒯.ℰ. ℳ𝒶𝓀ℴ𝒶𝓁𝒶