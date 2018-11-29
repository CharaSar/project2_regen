# ReGeneration // Java4web // SKG2018
## Project 2



Collaborators
-------------

* Chara Sarianidou
* Konstantina Kaffe
* Dimitris Apostolou
* Panagiotis Loukas


# ReSTful Web App EFKA

That's a web app that provides the ability for a patient or a doctor to connect and organize their appointments.
It's basically a Spring Boot project with Web, JPA, Security, etc. that serves the front-end as
static assets `/resources/static/` and provides the models, repos, controllers, exceptions, services, DTOs for the back-end `/main/java/org/regeneration`.

## Database implementation

The project is capable of creating a MySQL database (defined in `application.properties`) and populate some sample data (`LoadDatabase.java`) the first time/every time it runs.






## Entities implementation

We implement the entities `Doctor`, `Citizen`, `Appointment`, `Specialty` and `User` that represent the database tables and their fields.

## REST API Documentation
//cotrollers

## Exceptions & Advices
//exceptions advices

## Services
//dto

## Repositories


## Security










We also implement the respective DTOs that help us collect data from various entities (`NewAppointmentDTO`, `EditAppointmentDTO` & `UserRegistrationDTO`).

There is an implementation of the user page (`/pages/user/index.html`) that uses JQuery to retrieve Books
and display them.

The `BookController` implements a CRUD API that uses the `BookReppository` to manipulate data in the database.

## Running the project

That's a maven Spring Boot application so you can run it like that.

```
$ mvn spring-boot:run
```

After the server is up with the following URL with your browser.

```
http://localhost:8080
```
For example, you can login as `doc1` with password `doc1password` as a doctor or `cit1` and `cit1password` as a citizen. The user will be redirected automatically to the appropriate homepage according to his role.

## API Security

The project uses Spring Security in order to protect the API (`/api/**`). The login button will `POST`
the username and password (as `multipart/form-data`) to the `/login` servlet provided by Spring Security.

```
curl --request POST \
  --url http://localhost:8080/login \
  --header 'content-type: application/x-www-form-urlencoded' \
  --data 'username=cit1&password=cit1password'
```

After successful authentication all subsequent API calls *from the same origin* will be authorized. Spring Security also provides a `/logout`
servlet that expires the session.

Client side, a `sessionStorage` is used to hold the logged in username and the user's role. That's only there to avoid having
to hit the server each time the front-end needs to know if a user is logged in. Upon logout the `username` and `user_role` is removed from storage.