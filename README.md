# BloodLink – Blood Donation Management System

BloodLink is a full-stack blood donation management system designed to help manage blood banks, hospitals, donors, and blood requests.

The system consists of a **Spring Boot backend API** and a **JavaFX desktop frontend application**, allowing hospitals and staff to manage blood donation processes efficiently.

This project was developed as part of a Computer Science academic project.

---

## Technologies Used

### Backend
- Java
- Spring Boot
- Maven
- REST API
- MySQL
- JPA / Hibernate

### Frontend
- Java
- JavaFX
- Maven

---

## Features

### Blood Bank Management
- Create blood bank records
- View all blood banks
- Update blood bank information
- Delete blood bank records
- Search blood banks by name

### Blood Request Management
- Create blood requests
- Track request status
- View requests by hospital

### Donor Management
- Register donors
- Login authentication
- View donor information
- Delete donors

### Hospital Management
- Hospital login system
- Manage hospital information

### Donation Tracking
- Record blood donations
- Track donation status
- View donations by donor

### Staff Management
- Create and manage staff accounts
- Staff authentication

---

## Project Structure


```
bloodlink-blood-donation-system
│
├── backend
│   ├── src
│   ├── pom.xml
│   ├── mvnw
│   └── mvnw.cmd
│
├── frontend
│   ├── src
│   ├── pom.xml
│   ├── mvnw
│   └── mvnw.cmd
│
└── README.md
```


- **backend** → Spring Boot REST API  
- **frontend** → JavaFX desktop application  

---

## Backend API Endpoints

### Blood Banks

```
GET /api/bloodbanks
GET /api/bloodbanks/{id}
POST /api/bloodbanks
PUT /api/bloodbanks/{id}
DELETE /api/bloodbanks/{id}
GET /api/bloodbanks/search?name={name}
```


### Blood Requests

```
GET /api/bloodrequests
GET /api/bloodrequests/{id}
POST /api/bloodrequests
PUT /api/bloodrequests/{id}
PATCH /api/bloodrequests/{id}/status
DELETE /api/bloodrequests/{id}
GET /api/bloodrequests/byHospital?hospitalName={name}
```


### Donations

```
GET /api/donations
GET /api/donations/{id}
POST /api/donations
PUT /api/donations/{id}
PATCH /api/donations/{id}/status
DELETE /api/donations/{id}
GET /api/donations/byDonor?username={username}
```


### Donors

```
GET /api/donors/all
GET /api/donors/{id}
GET /api/donors/username/{username}
POST /api/donors/create
POST /api/donors/login
PUT /api/donors/update/{id}
DELETE /api/donors/delete/{id}
```



### Hospitals

```
GET /api/hospitals
GET /api/hospitals/{id}
POST /api/hospitals
PUT /api/hospitals/{id}
DELETE /api/hospitals/{id}
POST /api/hospitals/login
```


### Staff

```
GET /api/staff/all
GET /api/staff/{id}
POST /api/staff/create
PUT /api/staff/update/{id}
DELETE /api/staff/delete/{id}
POST /api/staff/login
```


---

## Running the Backend

1. Open the **backend** folder in your IDE.
2. Run the Spring Boot main application class.
3. The server will start on:


```
http://localhost:8080
```

---

## Database Setup

Create a MySQL database:

```
bloodlink_db
```

Update the configuration in:

```
backend/src/main/resources/application.properties
```

Example configuration:

```
spring.datasource.url=jdbc:mysql://localhost:3306/bloodlink_db
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

---

## Purpose of the Project

The goal of this project was to practice building a full-stack system using:

- Spring Boot for backend development
- JavaFX for frontend UI
- RESTful API architecture
- Database integration using MySQL

---

## Author

Computer Science Student  
Developed as part of an academic software engineering project.
