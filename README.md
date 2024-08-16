# Domain-Driven Design (DDD) - Soda machine

## Design goals

The soda machine utilizes the method of Domain-Driven Design (DDD) of Eric Evans. The goal of this project is to show how the out-of-the-box tools of Spring can be used to make a proper clean domain model in DDD. 

### In detail

The soda machine shows how domain objects called aggregates in DDD can be modeled using a relational database. All the aggregates propagate their states via events and so the architecture is event-driven. This also means that each aggregate has a transactional context and the states propagated in between aggregates are eventually consistent and take time to propagate. To support eventing, I used Spring events in the backend and web sockets for the frontend.

### Out of scope

However, I will not show how to create bounded contexts. Though, I suggest to use an event queue using Spring Cloud Stream, which helps to establish an eventing mechanism in an abstract way. As well I don't use event sourcing, though this would be a small change to the code base. It is due to the reader to work through the documentation of event sourcing.

## Architecture

### Hexagonal architecture

The hexagonal architecture uses a domain model as its core and connects these via adapters to the environment. This is a fundamental change to the multiple layer architecture, in which all the layers had concerns on its own. In hexagonal architecture all the components like interfaces and UIs are isolated via adapters in the application layer, which can be connected via ports to the domain model.

![Hexagonal architecture is used for the application](hexagonal.png)

### Event model

The event flow was created using the event-storming method of Alberto Brandolini, which helps to properly design the application events upfront in an unobtrusive way by using events as the first-class citizen.

![Eventstorming is used for design of events](eventstorming.png)

## Technology decisions

The following technologies are used:

* Spring Boot with Spring Data / Web
* Thymeleaf with Webjars
* Websockets with SockJs und Stomp
* H2 Database with Flyway, Lombok
* At least Java with JDK 21

## Links

I recommend the following links to DDD:

* [Tactical domain-driven design (software-architektur.tv)](https://software-architektur.tv/2024/05/03/folge214.html)
* [Domain-driven Design (Martin Fowler)](https://martinfowler.com/bliki/DomainDrivenDesign.html)
* [DDD starter modeling process](https://github.com/ddd-crew/ddd-starter-modelling-process)
