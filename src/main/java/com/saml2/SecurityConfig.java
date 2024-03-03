package com.saml2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistration;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.registration.InMemoryRelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrations;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.util.Assert;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter { 

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests(authorize -> authorize
            	.antMatchers("/css/**", "/js/**", "/img/**").permitAll()
                .antMatchers("/").permitAll()  // Permit unauthenticated access to the home page
                .anyRequest().authenticated()  // Require authentication for any other request
            )
            .saml2Login(saml2Login -> saml2Login
                .loginPage("/saml2/authenticate/miIdp")
                .defaultSuccessUrl("/loginSuccess", true)
            )
            .logout(logout -> logout
                .logoutSuccessHandler(logoutSuccessHandler())  // Handle custom logout
            );
    }

    private LogoutSuccessHandler logoutSuccessHandler() {
        return (request, response, authentication) -> {
            // Redirect to the home page after logout
            response.sendRedirect("/");
        };
    }

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
}
    
