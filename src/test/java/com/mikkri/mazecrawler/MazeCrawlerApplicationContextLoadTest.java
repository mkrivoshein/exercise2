package com.mikkri.mazecrawler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MazeCrawlerApplicationContextLoadTest {
	@MockBean
	private CommandLineRunner commandLineRunner;

	@Test()
	public void contextLoad() throws Exception {
		Mockito.verify(commandLineRunner).run();
	}

}
