package ee.elisa.gamechannel;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ee.elisa.gamechannel.service.PlayerGameSessionTest;
import ee.elisa.gamechannel.service.PlayerGridTest;
import ee.elisa.gamechannel.util.RandomTest;

@RunWith(Suite.class)
@SuiteClasses({PlayerGridTest.class,PlayerGameSessionTest.class,RandomTest.class})
public class AllTests {

}
