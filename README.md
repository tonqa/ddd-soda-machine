# Domain-Driven Design (DDD) - Soda machine

## Design goals

The soda machine utilizes the method of Domain-Driven Design (DDD) of Eric Evans. The goal of this project is to show how we can use the out-of-the-box tools of Spring to make a proper clean domain model in DDD. More to say it shows how domain objects called aggregates in DDD can be modeled in a relational database. The aggregates propagate their states via events and so the architecture is event-driven. This also means tha the aggregate is a transactional context and the states propagated in between aggregates are eventually consistent and take time to propagate. To support eventing, we also use web sockets in the frontend.

However, we will not show how to create bounded contexts here. Though, I suggest to use a event queue using Spring Cloud Stream, which is quite new and helps to establish an eventing mechanism in an abstract way. We don't use event sourcing, though this would be a small change to the code base. It is due to the reader to work through the documentation of event sourcing.

## Event model

The event model was created using the event-storming method of Alberto Brandolini, which helps to properly plan the design of the application upfront in an unobtrusive way by using events as the first-class citizen.

![Eventstorming is used to show the application design](eventstorming.png)

## Technology decisions

The following technologies are used:

* Spring Boot with Spring Data / Web
* Thymeleaf with Webjars
* Websockets with SockJs und Stomp
* H2 Database with Flyway, Lombok

## Links

We recommend the following links to DDD:

* [Tactical domain-driven design (software-architektur.tv)](https://software-architektur.tv/2024/05/03/folge214.html)
* [Domain-driven Design (Martin Fowler)](https://martinfowler.com/bliki/DomainDrivenDesign.html)
* [DDD starter modeling process](https://github.com/ddd-crew/ddd-starter-modelling-process)