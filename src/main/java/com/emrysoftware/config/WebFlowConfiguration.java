package com.emrysoftware.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.faces.config.AbstractFacesFlowConfiguration;
import org.springframework.faces.webflow.FlowFacesContextLifecycleListener;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.mvc.servlet.FlowHandlerAdapter;
import org.springframework.webflow.mvc.servlet.FlowHandlerMapping;
import org.springframework.webflow.security.SecurityFlowExecutionListener;

@Configuration
public class WebFlowConfiguration extends AbstractFacesFlowConfiguration {

	/**
	 * Configure Web Flow for JSF
	 */
	protected FlowBuilderServices flowBuilderServicesBuilder() {
		return getFlowBuilderServicesBuilder()
		        .setDevelopmentMode ( true )
		        .build();
	}
	
	protected FlowDefinitionRegistry flowRegistry() {
        return getFlowDefinitionRegistryBuilder ()
                .setBasePath ( "/WEB-INF/flows" )
                .addFlowLocationPattern ( "/**/*-flow.xml" )
                .setFlowBuilderServices ( flowBuilderServicesBuilder() )
                .build ();
    }
    
	protected FlowExecutor flowExecutor() {
        return getFlowExecutorBuilder ( flowRegistry () )
                .addFlowExecutionListener ( new FlowFacesContextLifecycleListener () )
                .addFlowExecutionListener ( new SecurityFlowExecutionListener () )
                .build ();
    }
    
    /**
     * Link Web Flow into MVC.
     * These need to be exposed with @Bean
     */
	@Bean
	public FlowHandlerMapping flowHandlerMapping() {
		FlowHandlerMapping handlerMapping = new FlowHandlerMapping();
		handlerMapping.setOrder(-1);
		handlerMapping.setFlowRegistry(flowRegistry());
		return handlerMapping;
	}
	
	@Bean
	public FlowHandlerAdapter flowHandlerAdapter() {
		FlowHandlerAdapter handlerAdapter = new FlowHandlerAdapter();
		handlerAdapter.setFlowExecutor(flowExecutor());
		handlerAdapter.setSaveOutputToFlashScopeOnRedirect(true);
		return handlerAdapter;
	}

}