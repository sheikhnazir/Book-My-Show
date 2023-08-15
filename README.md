# Book-My-Show
BookMyShow Project App

BookMyShow is a Spring Boot application that simplifies the movie ticket booking process. Users can add movies, manage theaters, shows, seats, and book/cancel tickets and much more.

Features
Add movies, theaters, and shows.
Manage user accounts and authentication.
Book and cancel movie tickets.
Assign and manage theater show seats.
Included 20 more features that i have implemented.
Getting Started
Prerequisites
Java JDK 
Spring Boot 
MySQL Database 
Maven

Installation
Clone this repository to your local machine.

bash
Copy code
git clone https://github.com/sheikhnazir/BookMyShow.git

Usage
API Documentation

Example API endpoints:

POST /api/movies: Add a new movie.
POST /api/users: Register a new user.
POST /api/theaters: Add a new theater.
POST /api/shows: Add a new show.
POST /api/bookings: Book tickets for a show.
DELETE /api/bookings/{bookingId}: Cancel a booked ticket.
POST /api/seats: Add seats to a theater show.
Database Schema
 brief overview of  database schema: -> MySQL is used to store all the data that we fetch, retrieve and add through api's. Database and mappings are very well implemented
