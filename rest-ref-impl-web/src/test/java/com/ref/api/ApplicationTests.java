package com.ref.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ApplicationTests {

	private static final Logger logger = LogManager.getLogger(ApplicationTests.class);

	@Rule
	public OutputCapture output = new OutputCapture();

	@Test
	public void testLogger() {
		logger.info("Hello World");

	}

}