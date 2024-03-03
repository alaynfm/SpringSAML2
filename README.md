# SpringSAML2
Spring SAML2 auth with Okta idp

## Dependencies

```yml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-saml2-service-provider</artifactId>
    <version>5.8.10</version>
</dependency>
```

- `spring-boot-starter-web`: Starter for building web applications, including RESTful applications using Spring MVC.
- `spring-boot-starter-security`: Starter for building secure Spring applications with authentication and authorization.
- `spring-security-saml2-service-provider`: Spring Security extension for SAML 2.0 service provider support.

## SAML Configuration (local IDP.xml)
```yml
miIdp:
  registrationId: miIdp
  metadataLocation: classpath:/metadata/idp-metadata.xml
```

- `@Value("${miIdp.registrationId}")`: Injects the value of `registrationId` from the application properties file.
- `@Value("${miIdp.metadataLocation}")`: Injects the value of `metadataLocation` from the application properties file.

### Java Configuration Explanation:

```java
@Value("${miIdp.registrationId}")
private String registrationId;
@Value("${miIdp.metadataLocation}")
private String metadataLocation;

@Bean
public RelyingPartyRegistrationRepository relyingPartyRegistrationRepository() {
    RelyingPartyRegistration registration = RelyingPartyRegistrations
        .fromMetadataLocation(metadataLocation)
        .registrationId(registrationId)
        .build();
    return new InMemoryRelyingPartyRegistrationRepository(registration);
}
```


This code configures a Spring bean that creates a relying party registration using the provided metadata location and registration ID, then returns an in-memory repository managing this relying party registration for use in SAML2 authentication.




