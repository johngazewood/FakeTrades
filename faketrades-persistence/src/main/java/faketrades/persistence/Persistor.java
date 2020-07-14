package faketrades.persistence;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.model.Record;

/*
 * CREATE EXTERNAL TABLE trades (
 * tradeId int,
 * amount string
 * ) LOCATION 's3://faketrades/production/trades/';
 */

/*
 * CREATE EXTERNAL TABLE events (
 * tradeId int,
 * eventId int,
 * eventType string,
 * version int
 * ) LOCATION 's3://faketrades/production/events/';
 */
@Component
public class Persistor {

	// TODO: Inject this.
	int poolSize = 5;

	ExecutorService service;

	public Persistor() {
		service = Executors.newFixedThreadPool(poolSize);
	}

	public void persist(List<Record> records, String targetDatabase, String targetTable) {
		Persist p = new Persist(records, targetDatabase, targetTable);
		service.submit(p);
	}

}
