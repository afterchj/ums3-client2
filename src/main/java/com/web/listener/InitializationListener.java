package com.web.listener;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.sf.ehcache.CacheManager;

import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;

import com.utils.Constants;
import com.utils.PropertiesLoader;

@Service
public class InitializationListener extends ContextLoader implements ServletContextListener{
	
	private static PropertiesLoader propertiesLoader = PropertiesLoader.getInstance();

	@Override
	public void contextInitialized(ServletContextEvent event) {
			String profiles = event.getServletContext().getInitParameter("spring.profiles.default");
			String customPath = "setup." + profiles + ".properties";
			propertiesLoader.setProperties("classpath:/setup.properties", customPath);
			Constants.TOMCAT_SHOW = propertiesLoader.getProperty("tomcat.show.location") + "/";
			Constants.STATIC_SHOW = propertiesLoader.getProperty("static.show.location");
			Constants.WORK_PIC = new File(propertiesLoader.getProperty("mem.work.pic"));
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		CacheManager.getInstance().shutdown();
	}


}
