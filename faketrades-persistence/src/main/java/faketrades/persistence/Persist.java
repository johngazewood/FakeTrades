package faketrades.persistence;

import java.util.List;

import com.amazonaws.services.dynamodbv2.model.Record;

public class Persist implements Runnable {

	List<Record> records;

	public Persist(List<Record> records) {
		this.records = records;
	}

	@Override
	public void run() {
		records.forEach(r -> System.out.println(Thread.currentThread().getName() + " let's persist " + r));

	}

}
