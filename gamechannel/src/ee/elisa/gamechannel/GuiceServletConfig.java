package ee.elisa.gamechannel;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.sun.jersey.core.util.FeaturesAndProperties;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

import ee.elisa.gamechannel.rest.resource.GameResource;
import ee.elisa.gamechannel.rest.resource.RootResource;
import ee.elisa.gamechannel.rest.resource.SettingsResource;
import ee.elisa.gamechannel.util.Random;

public class GuiceServletConfig extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new GuiceModule(), new JerseyServletModule() {
            @Override
            protected void configureServlets() {
                bind(RootResource.class);
                bind(GameResource.class);
                bind(SettingsResource.class);
                bind(Random.class);

                // Servlet init params
                final Map<String, String> params = new HashMap<String, String>();
                params.put(FeaturesAndProperties.FEATURE_FORMATTED, Boolean.TRUE.toString());
                serve("/api/*").with(GuiceContainer.class, params);
                super.configureServlets();
            }
        });
    }
    
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
    	ServletContext sc = servletContextEvent.getServletContext();
    	sc.setAttribute(Injector.class.getName(), getInjector());
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    	ServletContext sc = servletContextEvent.getServletContext();
    	sc.removeAttribute(Injector.class.getName());
    	super.contextDestroyed(servletContextEvent);
    }
}
