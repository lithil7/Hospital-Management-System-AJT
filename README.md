
# Hospital Management System

## Advanced Java Technology Mini Project

**Student Name:** P.L.N.GANESH  
**Enrollment Number:** 92500119092  
**Subject:** Advanced Java Technology (01CE0308)  
**College:** Marwadi University

## Project Overview

The Hospital Management System is a Java-based application developed to manage patient, doctor, and appointment records.

The project was developed in phases. Phase 2 focused on MySQL database design and JDBC operations. Phase 3 implemented Advanced Java technologies to provide web and desktop interfaces.

## Technologies Used

- Java
- Apache NetBeans
- MySQL
- JDBC
- Servlets
- JSP
- JSTL and Expression Language (EL)
- Spring MVC
- Spring Dependency Injection
- Hibernate ORM
- Java Swing
- Apache Tomcat 9
- Maven

## Main Features

### Patient Management
- View patient records
- Add new patients
- Delete test patient records

### Doctor Management
- View doctor details
- Add doctors
- Delete test doctor records

### Appointment Management
- Book appointments
- View appointment records
- Update appointment status
- Delete test appointments

### Additional Features
- Login and logout
- Session management using HttpSession
- Form validation
- JSP pages with JSTL and EL
- Hibernate database integration
- Spring MVC demonstration
- Java Swing desktop interface
- Database exception handling

## Database Structure

Database Name: `hospital_db`

The application uses three related MySQL tables:

1. `patients`
2. `doctors`
3. `appointments`

The appointments table connects patients and doctors using foreign keys.

## Software Requirements

- Apache NetBeans
- Java JDK 17 or a compatible version
- Apache Tomcat 9
- MySQL or XAMPP
- Maven
- Web browser

## How to Run the Project

1. Download or clone this repository.
2. Open the project in Apache NetBeans as a Maven Web Application.
3. Start MySQL using XAMPP.
4. Create the `hospital_db` database and its tables using the database schema from Phase 2.
5. Copy `src/main/resources/database.properties.example` to `database.properties`.
6. Update the database URL, username, and password for your MySQL installation.
7. Configure Apache Tomcat 9 in NetBeans.
8. Right-click the project and select Clean and Build.
9. Run the project.
10. Open the application in your browser.

Local application URL:

http://localhost:8080/HospitalManagementPhase3/

## Project Structure

- `hospital.phase3.db` - JDBC connection and database operations
- `hospital.phase3.web` - Servlets and session management
- `hospital.phase3.hibernate` - Hibernate ORM
- `hospital.phase3.spring` - Spring MVC and dependency injection
- `hospital.phase3.desktop` - Swing desktop application
- `WEB-INF/views` - JSP pages

## Testing

The application was developed and tested locally using Apache NetBeans, Apache Tomcat, and MySQL.

Only fictional test records should be used for demonstrations.

## Conclusion

The Hospital Management System demonstrates the integration of Java web technologies with MySQL. It provides basic hospital record management features through Servlets, JSP, JDBC, Hibernate, Spring, and Swing.

This project was developed as part of the Advanced Java Technology course at Marwadi University.
