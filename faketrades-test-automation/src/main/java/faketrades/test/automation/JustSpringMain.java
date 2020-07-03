package faketrades.test.automation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import faketrades.test.automation.framework.TestSuite;
import faketrades.test.automation.framework.TestSuiteResults;

@SpringBootApplication
public class JustSpringMain {

	@Autowired
	TestSuite testSuite;

	public static void main(String[] args) {
		SpringApplication.run(JustSpringMain.class, args);
	}

	@Bean
	public TestSuiteResults testSuiteResults() {
		return testSuite.runTestSuite();
	}

	@Bean
	public Boolean publishResults(ApplicationContext ctx) {
		boolean r = true;
		TestSuiteResults results = ctx.getBean(TestSuiteResults.class);
		System.out.println("JustSpringMain>> results : ");
		results.getTestResults().forEach(result -> System.out.println("\tresult: " + result.isPassed()));
		return r;
	}
}
