# Hotel Reservation System

This is a Hotel Reservation System developed using Java 17, Spring Boot, and REST API. The system is designed to handle a hotel chain with 5000 hotels and 1 million rooms, with an average occupancy rate of 70% and a duration of 3 days. The system also supports 10% overbooking and dynamic hotel pricing.

The system is divided into several microservices, including the Hotel Service, Reservation Service, Rate Service, Payment Service, and Hotel Management Service. The Hotel and Reservation services are under development, with the other services planned for future development.

## Technology Stack

The system will use some of the following technology stacks:

* Java 17
* Spring Boot
* REST API
* Kafka for asynchronous communication between the Hotel and Reservation services
* H2 database for testing
* PostgreSQL database for production
* Redis for caching
* Docker and Kubernetes for containerization and orchestration
* Jenkins for CI/CD pipeline
* JUnit for TDD, unit testing, and integration testing
* Maven for dependency management

The system will also implement an API gateway and service discovery for routing and communication between microservices.

## Functionality

The Hotel Reservation System supports the following functionality:

* Search for available hotel rooms
* Add, remove, and update hotels and rooms
* Show available hotel rooms
* Support 10% overbooking
* Dynamic hotel pricing
* High concurrency support
* Moderate latency support
* Resilience
* Scalability
* Eventual consistency

## System Design

The current system design mockup can be viewed at the following link:  
https://miro.com/app/board/uXjVMWh0aE8=/?share_link_id=715629588373

## Future Work

The system is under development, with several microservices planned for future implementation. Some of the planned features include:

Rate Service to handle pricing and promotions
Payment Service to handle payments and refunds
Hotel Management Service to manage hotels and rooms
The system will implement several monitoring tools to track the health and performance of the microservices:
  * Metrics and logging using Prometheus and Grafana
  * Distributed tracing using Jaeger or Zipkin
  * Health checks using Spring Boot Actuator or Kubernetes probes
  * Alerting
  * Dashboard

## Conclusion

The goal is to create a scalable, resilient, and performant system that provides a solid foundation for managing a large hotel chain. The use of microservices, containerization, and monitoring tools will ensure that the system can handle high traffic and maintain high availability.

## Disclaimer

The design idea for this project is based on Chapter 7 of the book "System Design Interview" by Alex Xu and Sahn Lam. While the implementation of the system is my own, the original concept and design came from this book. I highly recommend reading the book for more in-depth information on system design and interview preparation.
