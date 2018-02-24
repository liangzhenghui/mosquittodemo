package log4j;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

public class Log4jTest {
	private static Logger logger=Logger.getLogger(Log4jTest.class);
     @Before
     public void setUp() {
    	 
     }

	@Test
	public void testGet(){
		logger.info("info");
		logger.debug("debug");
	}

}
