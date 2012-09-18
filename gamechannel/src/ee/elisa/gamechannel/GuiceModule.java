package ee.elisa.gamechannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;

import ee.elisa.gamechannel.service.GameService;
import ee.elisa.gamechannel.util.Random;

public class GuiceModule extends AbstractModule {
    private static final Logger LOG = LoggerFactory.getLogger(GuiceModule.class);

    @Override
    protected void configure() {
        // DOMConfigurator.configure(this.getClass().getClassLoader().getResource("game-log4j.xml"));

    	bind(GameService.class).asEagerSingleton();
        bind(Random.class);
    	
        LOG.info("Starting gamechannel ...");
    }
}
