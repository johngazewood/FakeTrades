package com.faketrades.api.trade;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.faketrades.domain.FakeTrade;

import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.kinesis.KinesisAsyncClient;
import software.amazon.awssdk.services.kinesis.model.DescribeStreamRequest;
import software.amazon.awssdk.services.kinesis.model.DescribeStreamResponse;
import software.amazon.awssdk.services.kinesis.model.PutRecordRequest;
import software.amazon.awssdk.services.kinesis.model.PutRecordResponse;
import software.amazon.kinesis.common.KinesisClientUtil;

@Component
public class Trader {

	@Value("${stream.name}")
	String streamName;

	String shardIteratorType = "TRIM_HORIZON";

	@Value("${region}")
	String regionString;

	KinesisAsyncClient kinesisClient;

	@PostConstruct
	public void init() {
		Region region = Region.of(regionString);
		kinesisClient = KinesisClientUtil.createKinesisAsyncClient(KinesisAsyncClient.builder().region(region));

		/*
		KinesisAsyncClientBuilder clientBuilder = KinesisAsyncClient.builder().region(region);
		clientBuilder.httpClient(NettyNioAsyncHttpClient.builder()
				.buildWithDefaults(AttributeMap.builder().build()));
		((SdkClientBuilder) clientBuilder)
				.endpointOverride(URI.create("arn:aws:kinesis:us-east-2:964183462461:stream/FakeTradesTestStream"));
		kinesisClient = clientBuilder.build();
		*/
		
		
		validateStream();
	}

	public static void main(String[] args) {
		System.out.println("Trader>> running Trader directly.");
		Trader trader = new Trader();
		trader.setRegionString("us-east-2");
		trader.setStreamName("FakeTradesTestStream");
		trader.init();
		System.out.println("Trader>> -------------------------  ran Trader DIRECTLY.");
	}

	private void validateStream() {
		// DefaultAWSCredentialsProviderChain x = new
		// DefaultAWSCredentialsProviderChain();
		// System.out.println(String.format("Trader>> credentials: %s",
		// x.getCredentials()));

		
		DefaultCredentialsProvider provider = DefaultCredentialsProvider.create();
		AwsCredentials creds = provider.resolveCredentials();
		System.out.println(String.format("Trader>> creds %s -----------------------", creds));
		System.out.println(
				String.format("Trader>> property %s -----------------------", System.getProperty("accessKeyId")));
		
		DescribeStreamRequest request = DescribeStreamRequest.builder().streamName(streamName).build();
		CompletableFuture<DescribeStreamResponse> futureResponse = kinesisClient.describeStream(request);
		try {
			DescribeStreamResponse response = futureResponse.get();
			if (!response.streamDescription().streamStatus().toString().contentEquals("ACTIVE")) {
				System.err.println("Stream " + streamName + " is not active. Please wait a few moments and try again.");
			} else {
				System.out.println("Trader>> STREAM OK -------------------------------------------------------- ");
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

	}

	public String create(FakeTrade trade) {
		String kinesisId = "-1";
		byte[] bytes = trade.toJsonAsBytes();
		if (bytes == null) {
			System.err.println("Trader>> Could not get JSON bytes for trade: " + trade);
			return kinesisId;
		}
		Random rand = new Random();
		int partitionKey = rand.nextInt();
		PutRecordRequest request = PutRecordRequest.builder()
				.partitionKey(partitionKey + "")
				.streamName(streamName).data(SdkBytes.fromByteArray(bytes)).build();
		CompletableFuture<PutRecordResponse> futureResponse = kinesisClient.putRecord(request);
		PutRecordResponse response = null;
		try {
			response = futureResponse.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			return kinesisId;
		}
		System.out.println("Trader>> Please, validate kinesis put response: " + response);
		kinesisId = response.shardId() + ":" + response.sequenceNumber();
		return kinesisId;
	}

	public String getStreamName() {
		return streamName;
	}

	public void setStreamName(String streamName) {
		this.streamName = streamName;
	}

	public String getShardIteratorType() {
		return shardIteratorType;
	}

	public void setShardIteratorType(String shardIteratorType) {
		this.shardIteratorType = shardIteratorType;
	}

	public String getRegionString() {
		return regionString;
	}

	public void setRegionString(String regionString) {
		this.regionString = regionString;
	}

	public KinesisAsyncClient getKinesisClient() {
		return kinesisClient;
	}

	public void setKinesisClient(KinesisAsyncClient kinesisClient) {
		this.kinesisClient = kinesisClient;
	}


}
