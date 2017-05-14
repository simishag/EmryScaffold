package com.emrysoftware.config;

import org.omnifaces.resourcehandler.UnmappedResourceHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.faces.mvc.JsfView;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

/**
 * General MVC configuration.
 * Sets up static pages and JSF for views.
 * Configures dispatcher at specified prefix.
 */
@EnableWebMvc
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {
	
	@Value("${spring.resources.static-locations}")
	private String STATIC_LOCATIONS;
	
	@Value("${spring.mvc.static-path-pattern}")
	private String STATIC_PATH_PATTERN;
	
	UnmappedResourceHandler h;

	/**
	 * @EnableWebMvc does not seem to handle static resources as claimed
	 * by the docs. Source for WebMvcConfigurationSupport says:
	 * "Override this method to add resource handlers for serving static resources."
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String locations[] = STATIC_LOCATIONS.split(",");
		registry.addResourceHandler(STATIC_PATH_PATTERN).addResourceLocations(locations);
		
		// workaround for bootsfaces & wrong font dir in its bundled css
		// may be fixed in 1.1
		registry.addResourceHandler("/fonts/**").addResourceLocations("classpath:/META-INF/resources/fonts/");
		
	}
	
	@Bean
	public ViewResolver urlBasedViewResolver() {
		UrlBasedViewResolver urlBasedViewResolver = new UrlBasedViewResolver();
		urlBasedViewResolver.setViewClass(JsfView.class);
		urlBasedViewResolver.setPrefix("/WEB-INF/views/");
		urlBasedViewResolver.setSuffix(".xhtml");		
		return urlBasedViewResolver;
	}

	@Bean
	public DispatcherServlet dispatcherServlet() {
		return new DispatcherServlet();
	}

	@Bean
	public ServletRegistrationBean dispatcherServletRegistration() {
		ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet());
		registration.setName(DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME);
		return registration;
	}

}