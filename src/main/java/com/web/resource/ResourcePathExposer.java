package com.web.resource;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.utils.PropertiesLoader;

@Component("rpe")
public class ResourcePathExposer implements ServletContextAware {
	private ServletContext servletContext;
	private String resourceRoot;
	private static PropertiesLoader propertiesLoader = PropertiesLoader.getInstance();

	@PostConstruct
	public void init() {
		resourceRoot = "/resources-" + propertiesLoader.getProperty("system.version", "3.0.0");
		getServletContext().setAttribute("resourceRoot", getServletContext().getContextPath() + resourceRoot);
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public String getResourceRoot() {
		return resourceRoot;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

}
