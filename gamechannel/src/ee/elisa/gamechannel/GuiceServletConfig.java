package ee.elisa.gamechannel;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.sun.jersey.core.util.FeaturesAndProperties;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

import ee.elisa.gamechannel.rest.resource.GameResource;
import ee.elisa.gamechannel.rest.resource.RootResource;

public class GuiceServletConfig extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new GuiceModule(), new JerseyServletModule() {
            @Override
            protected void configureServlets() {
                bind(RootResource.class);
                bind(GameResource.class);
                // bind(JAXBJsonContextResolver.class);

                // Servlet init params
                final Map<String, String> params = new HashMap<String, String>();
                params.put(FeaturesAndProperties.FEATURE_FORMATTED, Boolean.TRUE.toString());
                serve("/*").with(GuiceContainer.class, params);
                super.configureServlets();
            }
        });
    }
}
