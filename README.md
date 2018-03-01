spring-boot-security-example
============================

This project demonstrates usage of Spring-Boot with Spring-Security using Java configuration with Integration Tests

Please read my post on our [Technical Blog](http://www.future-processing.pl/blog/exploring-spring-boot-and-spring-security-custom-token-based-authentication-of-rest-services-with-spring-security-and-pinch-of-spring-java-configuration-and-spring-integration-testing/)

Notice
======

The solutions provided here are examples to demonstrate the configuration of Spring Security. It is not recommended to use these examples as-is in production systems.

The method provided to protect Spring Boot Actuator endpoints is not recommended for production applications since newer Spring Boot versions have a built-in solution for protecting the actuator endpoints [(doc for 1.5.10)](https://docs.spring.io/spring-boot/docs/1.5.10.RELEASE/reference/html/production-ready-monitoring.html#production-ready-health-access-restrictions) [(doc for 2.0.0)](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html#production-ready-endpoints-security).

License
=======

    Copyright 2015 Future Processing Sp. z o.o.

    Licensed under The MIT License (MIT), see LICENSE.txt for details.
