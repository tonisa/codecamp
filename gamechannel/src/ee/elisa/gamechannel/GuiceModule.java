package ee.elisa.gamechannel;

import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;

import ee.elisa.gamechannel.service.GameService;

public class GuiceModule extends AbstractModule {
    private static final Logger LOG = LoggerFactory.getLogger(GuiceModule.class);

    @Override
    protected void configure() {
        // DOMConfigurator.configure(this.getClass().getClassLoader().getResource("game-log4j.xml"));

    	bind(GameService.class).asEagerSingleton();
    	
        LOG.info("Starting Offer module ...");

        // Conf

        // Data source pool
        // Names.bindProperties(binder(),
           //  Util.loadProperties(this.getClass().getClassLoader(), "offer-datasource.properties"));
        
        LOG.info("Offer module started.");
    }
}