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
    		
        	// ignore requests for jsf resources
    		.ignoring().antMatchers("/javax.faces.resource/**");
    }
    
    /*
     * Specific rules & mapping
     */
    @Override
    protected void configure ( HttpSecurity http ) throws Exception {
    	http    		
    		//.csrf ().disable ()
    		.authorizeRequests()
    			// pass thru static assets & error page
    			.antMatchers("/assets/**").permitAll()
    			.antMatchers("/error").permitAll()

				// catch anything else
				.anyRequest().hasRole("USER")
				
				// back to top
				.and()
			
			// configure login
	    	.formLogin ()
	            .loginPage ( "/login" ).permitAll ()
	            .usernameParameter ( "username" )
	            .passwordParameter ( "password" )
	            .loginProcessingUrl ( "/j_spring_security_login" )
	            .defaultSuccessUrl ( "/index", true )
	            .failureUrl ( "/login?login_error=true" )
	            
	            // back to top
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