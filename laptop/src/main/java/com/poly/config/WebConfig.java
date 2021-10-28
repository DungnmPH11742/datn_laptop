package com.poly.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/user-st/images/**").addResourceLocations("/resources/static/user-st/images/");
		registry.addResourceHandler("/static/user-st/css/**").addResourceLocations("/resources/static/user-st/css/");
		registry.addResourceHandler("/static/user-st/js/**").addResourceLocations("/resources/static/user-st/js/");
		registry.addResourceHandler("/static/user-st/fonts/**").addResourceLocations("/resources/static/user-st/fonts/");
	}
	
	
	
	
}
