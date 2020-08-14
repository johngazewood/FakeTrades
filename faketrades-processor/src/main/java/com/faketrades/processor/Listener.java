package com.faketrades.processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudwatch.CloudWatchAsyncClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.kinesis.KinesisAsyncClient;
import software.amazon.kinesis.common.ConfigsBuilder;
import software.amazon.kinesis.common.KinesisClientUtil;
import software.amazon.kinesis.coordinator.Scheduler;
import software.amazon.kinesis.processor.ShardRecordProcessor;
import software.amazon.kinesis.processor.ShardRecordProcessorFactory;
import software.amazon.kinesis.retrieval.polling.PollingConfig;

@Component
public class Listener {

	private static final Logger log = LoggerFactory.getLogger(Listener.class);

	@Value("${stream.name}")
	String streamName;

	@Value("${region}")
	String regionString;

	@Autowired
	FakeTradesProcessorDao dao;

	boolean processing = false;

	Region region;
	private KinesisAsyncClient kinesisClient;


	@PostConstruct
	void init() {
		this.region = Region.of(regionString);
		this.kinesisClient = KinesisClientUtil
				.createKinesisAsyncClient(KinesisAsyncClient.builder().region(this.region));

		validateStream();
		listen();
	}

//	public static void main(String[] args) {
//		Listener l = new Listener();
//		l.setRegionString("us-east-2");
//		l.setStreamName("FakeTradesTestStream");
//		l.init();
//		l.runDemo();
//	}

	private void runDemo() {
		DynamoDbAsyncClient dynamoClient = DynamoDbAsyncClient.builder().region(region).build();
		CloudWatchAsyncClient cloudWatchClient = CloudWatchAsyncClient.builder().region(region).build();
		ConfigsBuilder configsBuilder = new ConfigsBuilder(streamName, streamName, kinesisClient, dynamoClient,
				cloudWatchClient, UUID.randomUUID().toString(), new SampleRecordProcessorFactory());

		Scheduler scheduler = new Scheduler(configsBuilder.checkpointConfig(), configsBuilder.coordinatorConfig(),
				configsBuilder.leaseManagementConfig(), configsBuilder.lifecycleConfig(),
				configsBuilder.metricsConfig(), configsBuilder.processorConfig(),
				configsBuilder.retrievalConfig().retrievalSpecificConfig(new PollingConfig(streamName, kinesisClient)));

		Thread schedulerThread = new Thread(scheduler);
		schedulerThread.setDaemon(true);
		schedulerThread.start();

		System.out.println("Press enter to shutdown");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			reader.readLine();
		} catch (IOException ioex) {
			log.error("Caught exception while waiting for confirm. Shutting down.", ioex);
		}

		Future<Boolean> gracefulShutdownFuture = scheduler.startGracefulShutdown();
		log.info("Waiting up to 20 seconds for shutdown to complete.");
		try {
			gracefulShutdownFuture.get(20, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			log.info("Interrupted while waiting for graceful shutdown. Continuing.");
		} catch (ExecutionException e) {
			log.error("Exception while executing graceful shutdown.", e);
		} catch (TimeoutException e) {
			log.error("Timeout while waiting for shutdown.  Scheduler may not have exited.");
		}
		log.info("Completed, shutting down now.");
	}

	private class SampleRecordProcessorFactory implements ShardRecordProcessorFactory {
		public ShardRecordProcessor shardRecordProcessor() {
			return new FakeTradesShardProcessor(dao);
		}
	}

	private void validateStream() {
		System.out.println("Listener>> Validate The Stream.");
	}

	private void listen() {
		DynamoDbAsyncClient dynamoClient = DynamoDbAsyncClient.builder().region(region).build();
		CloudWatchAsyncClient cloudWatchClient = CloudWatchAsyncClient.builder().region(region).build();
		ConfigsBuilder configsBuilder = new ConfigsBuilder(streamName, streamName, kinesisClient, dynamoClient,
				cloudWatchClient, UUID.randomUUID().toString(), new SampleRecordProcessorFactory());

		Scheduler scheduler = new Scheduler(configsBuilder.checkpointConfig(), configsBuilder.coordinatorConfig(),
				configsBuilder.leaseManagementConfig(), configsBuilder.lifecycleConfig(),
				configsBuilder.metricsConfig(), configsBuilder.processorConfig(),
				configsBuilder.retrievalConfig().retrievalSpecificConfig(new PollingConfig(streamName, kinesisClient)));

		Thread schedulerThread = new Thread(scheduler);
		schedulerThread.setDaemon(true);
		schedulerThread.start();
	}

	public String getStreamName() {
		return streamName;
	}

	public void setStreamName(String streamName) {
		this.streamName = streamName;
	}

	public String getRegionString() {
		return regionString;
	}

	public void setRegionString(String regionString) {
		this.regionString = regionString;
	}

	public boolean isProcessing() {
		return processing;
	}

	public void setProcessing(boolean processing) {
		this.processing = processing;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public KinesisAsyncClient getKinesisClient() {
		return kinesisClient;
	}

	public void setKinesisClient(KinesisAsyncClient kinesisClient) {
		this.kinesisClient = kinesisClient;
	}

	
	
}
