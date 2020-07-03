package faketrades.test.automation.framework;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import faketrades.test.automation.test.TestKinesisWriter;

@Component
public class TestSuite {

	public TestSuiteResults runTestSuite() {
		System.out.println("TestSuite>> Please, actually run tests");
		TestSuiteResults results = new TestSuiteResults();
		List<AutomatedTest> tests = new LinkedList<AutomatedTest>();
		tests.add(testKinesisWriter);
		// TODO: Use Thread executor service...
//		ThreadPoolExecutor executor = new ThreadPoolExecutor();

		try {
//			executor.invokeAll(tests);
			AutomatedTestResult tr = testKinesisWriter.call();
			results.add(tr);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("TestSuite>> Exception during executor.invokeAll: " + e.getMessage());
			System.out.println(1);
		}

		return results;
	}

	@Autowired
	TestKinesisWriter testKinesisWriter;

}
