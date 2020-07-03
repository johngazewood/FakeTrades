package faketrades.test.automation.framework;

import java.util.LinkedList;
import java.util.List;

public class TestSuiteResults {
	List<AutomatedTestResult> testResults;

	public List<AutomatedTestResult> getTestResults() {
		if (testResults == null) {
			testResults = new LinkedList<AutomatedTestResult>();
		}
		return testResults;
	}

	public void setTestResults(List<AutomatedTestResult> testResults) {
		this.testResults = testResults;
	}

	public void add(AutomatedTestResult testResult) {
		this.getTestResults().add(testResult);
	}

}
