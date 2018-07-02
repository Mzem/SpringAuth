package fr.aymax.evalApp.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		//return new Class[] { AppConfig.class };
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { AppConfig.class };
		//eturn null;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

}






