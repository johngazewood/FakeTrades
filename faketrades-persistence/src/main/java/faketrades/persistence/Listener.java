package faketrades.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBStreams;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBStreamsClientBuilder;
import com.amazonaws.services.dynamodbv2.model.DescribeStreamRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeStreamResult;
import com.amazonaws.services.dynamodbv2.model.GetRecordsRequest;
import com.amazonaws.services.dynamodbv2.model.GetRecordsResult;
import com.amazonaws.services.dynamodbv2.model.GetShardIteratorRequest;
import com.amazonaws.services.dynamodbv2.model.GetShardIteratorResult;
import com.amazonaws.services.dynamodbv2.model.Record;
import com.amazonaws.services.dynamodbv2.model.Shard;
import com.amazonaws.services.dynamodbv2.model.ShardIteratorType;

/*
 * Code mostly copied from https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Streams.LowLevel.Walkthrough.html
 * as of July 13, 2020.
 */
@Component
public class Listener {

	@Autowired
	private Persistor persistor;

	public void listen() {
		AmazonDynamoDBStreams streamsClient = AmazonDynamoDBStreamsClientBuilder.standard()
				.withRegion(Regions.US_EAST_2).withCredentials(new DefaultAWSCredentialsProviderChain()).build();
		String streamArn = System.getProperty("stream.arn");

		int maxItemCount = 10;

		String lastEvaluatedShardId = null;

		do {
			DescribeStreamResult describeStreamResult = streamsClient.describeStream(new DescribeStreamRequest()
					.withStreamArn(streamArn).withExclusiveStartShardId(lastEvaluatedShardId));
			List<Shard> shards = describeStreamResult.getStreamDescription().getShards();

			for (Shard shard : shards) {
				String shardId = shard.getShardId();
				GetShardIteratorRequest getShardIteratorRequest = new GetShardIteratorRequest().withStreamArn(streamArn)
						.withShardId(shardId).withShardIteratorType(ShardIteratorType.TRIM_HORIZON);
				GetShardIteratorResult getShardIteratorResult = streamsClient.getShardIterator(getShardIteratorRequest);
				String currentShardIter = getShardIteratorResult.getShardIterator();

				int processedRecordCount = 0;
				while (currentShardIter != null && processedRecordCount < maxItemCount) {

					GetRecordsResult getRecordsResult = streamsClient
							.getRecords(new GetRecordsRequest().withShardIterator(currentShardIter));
					List<Record> records = getRecordsResult.getRecords();
					if (!records.isEmpty()) {
						records.forEach(
								r -> System.out.println(Thread.currentThread().getName() + " persisting record: " + r));
						persistor.persist(records);
					}
					processedRecordCount += records.size();
					currentShardIter = getRecordsResult.getNextShardIterator();
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			// If LastEvaluatedShardId is set, then there is
			// at least one more page of shard IDs to retrieve
			lastEvaluatedShardId = describeStreamResult.getStreamDescription().getLastEvaluatedShardId();

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} while (lastEvaluatedShardId != null);
	}

	public Persistor getPersistor() {
		return persistor;
	}

	public void setPersistor(Persistor persistor) {
		this.persistor = persistor;
	}

}
