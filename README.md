# ComputerSystem ``Portfolio project``
Microservices for e-commerce computer components store

![build](https://img.shields.io/github/actions/workflow/status/iNikitaGricenko/ComputerSystem/build.yml?style=for-the-badge/badge.svg)
<br>

# Requirements:
- Java 17 or above
- Apache Maven 3.6.3 or above
- docker 24.0.2+ and docker-compose v2.17.2+

# (Linux) Instruction for run: 
* Open root project folder in console:
  - Execute comand: "mvn clean package"
  - Execute command: "docker-compose build"
  - Execute command: "docker-compose up -d"
* Open [Eureka](http://localhost:8761/) in browser to enter eureka
* Open [Swagger](http://localhost:8080/webjars/swagger-ui/index.html) in browser to open swagger for all services
* If elasticsearch won't run then product-service will not as well. Write this command in console to update max memory on virtual machine thats let elasticsearch run: `sysctl -w vm.max_map_count=262144`

To stop docker containers exxecute command (in console from previous steps) "docker-compose stope"

# Modules:
* ![Product Service](ProductService)
* ![Open Authentication 2 Server](OAuth2)
* ![Notification Service](NotificationService)
* ![Gateway Server](Gateway)
* ![Eureka Server](Eureka)
* ![Email Service](EmailServiceM)
* ![Customer Service](CustomerService)
* ![Jwt Authorization Server](AuthorizationServer)

# Technologies

## Architecture
* Microservice

## Backend
* Spring Boot
* Spring Web
* Gateway Server
* Spring Cloud
* Discovery Server (Netflix Eureka)
* OAuth2 Authorization Server
* OAuth2 Resource Server
* Spring Validation
* Spring Mail
* JWT
* Stripe
* Elasticsearch
* OpenFeign
* WebClient
* Loadbalacer
* DigitalOcean
* Apache Kafka
* Swagger
* Redis Caching
* JPA
* AspectJ

#### Mail Templates
* Template Engine - Thymeleaf

### Databases
#### SQL
* MySQL

#### NoSQL
* MongoDB
* Redis

#### Migrations
* Flyway

### Tests
* JUnit 4/5
* MockMVC

### Code Write
* Lombok
* Mapstruct

### Services
* Github actions
* Docker & Docker-compose

![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)

![Ubuntu](https://img.shields.io/badge/Ubuntu-E95420?style=for-the-badge&logo=ubuntu&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)

![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)

![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-%234ea94b.svg?style=for-the-badge&logo=mongodb&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)
![Redis](https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white)
![ElasticSearch](https://img.shields.io/badge/-ElasticSearch-005571?style=for-the-badge&logo=elasticsearch)

![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)

![PayPal](https://img.shields.io/badge/PayPal-00457C?style=for-the-badge&logo=paypal&logoColor=white)


[![CC BY-NC-ND 4.0][cc-by-nc-nd-shield]][cc-by-nc-nd]

This work is licensed under a
[Creative Commons Attribution-NonCommercial-NoDerivs 4.0 International License][cc-by-nc-nd].

[![CC BY-NC-ND 4.0][cc-by-nc-nd-image]][cc-by-nc-nd]

[cc-by-nc-nd]: http://creativecommons.org/licenses/by-nc-nd/4.0/
[cc-by-nc-nd-image]: https://licensebuttons.net/l/by-nc-nd/4.0/88x31.png
[cc-by-nc-nd-shield]: https://img.shields.io/badge/License-CC%20BY--NC--ND%204.0-lightgrey.svg
