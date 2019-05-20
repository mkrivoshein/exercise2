package com.mikkri.mazecrawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;

@SpringBootApplication
public class MazeCrawlerApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(MazeCrawlerApplication.class);
		ConfigurableEnvironment environment = new StandardEnvironment();
		// if this method is called, assume it is a standalone run rather than some unit test or executing
		// within context of some other application
		environment.setDefaultProfiles("standalone");
		application.setEnvironment(environment);
		application.run(args);
	}
}
