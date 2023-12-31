# Prescription-Management-System

Prescription Management System for SE 4458 Final Project

## Introduction

The "Prescription Management System" project employs a microservices architecture.
It consists of several backend services written in Java, using Spring Boot for efficient management and operation.
The project integrates `Amazon Simple Queue Service (SQS)` and `Azure Gateway`, indicating a focus on reliable
communication and service management. On the frontend, the client-side is developed using `Angular Framework`.
This combination of technologies and the microservices architecture suggests a focus on scalability and modular design,
essential for managing complex functionalities in a prescription management system.

## Technologies

- Java 17
- Spring Boot 3.2.x
- JWT for authentication
- Feign Client for inter-service communication
- Azure Cosmos NoSQL DB for database storage
- Azure API Gateway for routing requests
- Amazon Simple Queue Service (SQS) for queuing services
- Spring Cache for caching

## Basic Scenario

Pharmacists are registered in the system beforehand. Pharmacists log in to the system using an 11-digit ID to query the
user from the database. Then, they click the "Search Customer" button to look up the user. If the user exists in the
database, the user's fullname is entered into the field just above the button. Afterward, the user enters the keywords
for the desired medicine into the "Medicine" section to find medicines containing that keyword. They select the desired
medicines, add them to the cart, and simultaneously update the cart price. Once the pharmacist has selected the
medicines, they press the "Save" button to create the prescription. Every night at 01:00 AM, the system sends a Z-Score
report to the pharmacy.

## System Architecture Diagram
![se_4458_architecture](https://github.com/nuricanozturk01/Prescription-Management-System/assets/62218588/0c61c89e-c9b6-4de2-935c-44ae32e9d6db)

## Architecture Description

### API Gateway

- **API Gateway**: Serves as the entry point for users or other systems to access services. Routes all incoming
  requests.

### Microservices

- **Prescription/Pharmacy Service**: Manages prescriptions and pharmacy-related operations. Utilizes JWT (JSON Web
  Token) for secure communication.
- **Medicine Service**: Oversees medicine data and responds to medicine-related requests. Periodically sends emails,
  such as every Wednesday at 03:00 AM, to update data statuses.
- **Payment Service**: Handles payment processing and dispatches Z-Score reports to pharmacies nightly at 01:00 AM to
  pharmacies.
- **User/Customer Service**: Administers user and customer data. Capable of retrieving user information by ID searches.

### Databases

- **Medicine Azure NoSQL DB**: Stores medicine data.
- **Prescription Azure NoSQL DB**: Stores prescription data.
- **Customer Azure NoSQL DB**: Stores customer data.
- **Payment Azure NoSQL DB**: Stores payment transaction data.

### Other Components

- **Feign Client**: A Java HTTP client library for microservices communication over HTTP.
- **Payment Queue (Amazon SQS)**: Queues payments using Amazon Simple Queue Service (SQS) for message queue management.
- **Common Data Library**: Contains shared data structures or codes accessible to all microservices.
- **Spring Cache**: Enhances application performance through caching.
- **Spring Security**: Provides authentication and authorization for Spring applications.
- **Scheduled Tasks**: Executes tasks at a specified time or interval.
- **Spring Mail**: Sends emails using Spring Boot.
- **Angular**: Frontend framework for client-side development.

## Deployment
- **Azure:** Microservices, databases, API Gateway
- **AWS:** Angular, SQS

## ER Diagrams or Entity
### User:
- **customerId**: UUID,
- **identityNumber**: string,
- **customerName**: string,
- **customerSurname**: string

### Medicine:
- **name**: string,
- **barcode**: string,
- **atcCode**: string,
- **atcName**: string,
- **firmName**: string,
- **prescriptionType**: string

### Prescription:
- **id**: UUID,
- **name**: string,
- **username**: string,
- **email**: string
- **password**: string (hash)

### Payment:
- **paymentId**: UUID,
- **pharmacyUsername**: string,
- **pharmacyName**: string,
- **totalPrice**: double,
- **creationDate**: date
- **medicines**: Medicine[]

## Client Designs

### Login Page

![login_screen](https://github.com/nuricanozturk01/Prescription-Management-System/assets/62218588/09f1e4b4-100a-4248-8f43-7dc1ce095adb)

### Create Prescription Form

![create_prescription_db](https://github.com/nuricanozturk01/Prescription-Management-System/assets/62218588/217ac363-97b2-4279-8a9b-93c7eda1fd07)

### Pharmacy Z-Score email demonstration:
![email_example](https://github.com/nuricanozturk01/Prescription-Management-System/assets/62218588/cba78ffe-a7ce-48c1-bdc7-1bfe37e558e4)

### Update Medicines Email Demonstration:
![update_email](https://github.com/nuricanozturk01/Prescription-Management-System/assets/62218588/d1170e5f-a66e-47e6-8d02-2fb37ab86602)

## Videos
https://github.com/nuricanozturk01/Prescription-Management-System/assets/62218588/16924ec1-b3d5-44ef-98bf-cb1f2e0eda85
