package faketrades.persistence;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.model.Record;

@Component
public class Persistor {

	// TODO: Inject this.
	int poolSize = 5;

	ExecutorService service;

	public Persistor() {
		service = Executors.newFixedThreadPool(poolSize);
	}

	public void persist(List<Record> records) {
		Persist p = new Persist(records);
		service.submit(p);
	}

}
