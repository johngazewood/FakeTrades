package faketrades.test.automation.test;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import faketrades.domain.FakeTrade;
import faketrades.test.automation.framework.AutomatedTest;
import faketrades.test.automation.framework.AutomatedTestResult;
import faketrades.test.automation.kinesis.KinesisWriter;

@Component
public class TestKinesisWriter implements AutomatedTest {

	public static void main(String args[]) {
		FakeTrade trade = new FakeTrade();
		trade.setAmount(new BigDecimal("10000"));
		trade.setTradeId(2);
		KinesisWriter kinesisWriter = new KinesisWriter();
		kinesisWriter.sendFakeTrade(trade);
	}

	@Autowired
	KinesisWriter kinesisWriter;

	@Override
	public AutomatedTestResult call() throws Exception {
		FakeTrade trade = new FakeTrade();
		trade.setAmount(new BigDecimal("10000"));
		trade.setTradeId(2);
		Exception exception = null;
		try {
//			System.out.println("TestKinesisWriter>> just kidding... try sending again");
			kinesisWriter.sendFakeTrade(trade);
		} catch (Exception e) {
			exception = e;
		}
		AutomatedTestResult result = new AutomatedTestResult();
		if (exception == null) {
			result.setPassed(true);
		} else {
			result.setPassed(false);
			return result;
		}

		return result;
	}

}
