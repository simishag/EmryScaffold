package com.emrysoftware.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	/**
	 * Authentication. Can do simple in-memory users here.
	 */
    @Override
    protected void configure ( AuthenticationManagerBuilder auth ) throws Exception {
        auth.inMemoryAuthentication ()
                .withUser ( "user" ).password ( "user" ).roles ( "USER" )
                .and ()
                .withUser ( "admin" ).password ( "admin" ).roles ( "USER", "ADMIN" );
    }

    /**
     * General policy rules
     */
    @Override
    public void configure ( WebSecurity web ) throws Exception {
    	web
    		// set debug flag. dumps a lot of request data 
    		.debug(false)
    		
        	// ignore requests for jsf resources, fonts, error page
    		// probably don't want to add "assets" unless we create a 
    		// separate "public-assets" or something
    		.ignoring().antMatchers("/javax.faces.resource/**").and()
    		.ignoring().antMatchers("/fonts/**").and()
    		.ignoring().antMatchers("/favicon.ico").and()
    		.ignoring().antMatchers("/error")
    		;
    }
    
    /*
     * Specific rules & mapping
     */
    @Override
    protected void configure ( HttpSecurity http ) throws Exception {
    	http
    		.authorizeRequests()

				// catch anything else
				.anyRequest().hasRole("USER")
				
				// chain
				.and()
			
			// configure login
	    	.formLogin ()
	            .loginPage ( "/login" ).permitAll ()
	            .usernameParameter ( "username" )
	            .passwordParameter ( "password" )
	            .loginProcessingUrl ( "/j_spring_security_login" )
	            .defaultSuccessUrl ( "/index", true )
	            .failureUrl ( "/login?login_error=true" )
	            
	            // chain
	            .and()
	            
	        // configure logout
			.logout()
				.logoutUrl("/j_spring_security_logout")
				.logoutSuccessUrl("/login")
				.invalidateHttpSession(true)
				.permitAll()
    			;

    }
}