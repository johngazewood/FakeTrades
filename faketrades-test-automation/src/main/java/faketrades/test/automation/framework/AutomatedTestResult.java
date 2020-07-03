package faketrades.test.automation.framework;

public class AutomatedTestResult {
	private boolean passed = false;
	private String automatedTestFailureDescription;

	public boolean isPassed() {
		return passed;
	}

	public void setPassed(boolean passed) {
		this.passed = passed;
	}

	public String getAutomatedTestFailureDescription() {
		return automatedTestFailureDescription;
	}

	public void setAutomatedTestFailureDescription(String automatedTestFailureDescription) {
		this.automatedTestFailureDescription = automatedTestFailureDescription;
	}

}
