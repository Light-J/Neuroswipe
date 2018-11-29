package com.nsa.cubric.application;

import com.nsa.cubric.application.configurators.WebSecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@SpringBootApplication
public class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        LOG.debug("Starting swipe right brain application");
        SpringApplication.run(Application.class, args);
    }

    public class SpringApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

        protected Class<?>[] getRootConfigClasses() {
            return new Class[] {WebSecurityConfig.class};
        }

        @Override
        protected Class<?>[] getServletConfigClasses() {
            return new Class[0];
        }

        @Override
        protected String[] getServletMappings() {
            return new String[0];
        }
    }
}
