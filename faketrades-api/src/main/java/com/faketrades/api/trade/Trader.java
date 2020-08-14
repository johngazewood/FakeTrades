package com.faketrades.api.trade;

import java.nio.ByteBuffer;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;
import com.amazonaws.services.kinesis.model.DescribeStreamResult;
import com.amazonaws.services.kinesis.model.PutRecordResult;
import com.faketrades.domain.FakeTrade;

import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;

@Component
public class Trader {

	private static final Logger logger = LogManager.getLogger(Trader.class);

	@Value("${stream.name}")
	String streamName;

	String shardIteratorType = "TRIM_HORIZON";

	@Value("${region}")
	String regionString;

	boolean processing = false;

	AmazonKinesis kinesis;

	@PostConstruct
	public void init() {
		Regions region = Regions.fromName(regionString);
		DefaultCredentialsProvider provider = DefaultCredentialsProvider.create();
		AwsCredentials creds = provider.resolveCredentials();
		String accessKey = creds.accessKeyId();
		String secretKey = creds.secretAccessKey();
		BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
		kinesis = AmazonKinesisClientBuilder
				.standard()
				.withCredentials(
				new AWSStaticCredentialsProvider(awsCredentials))
				.withRegion(region).build();

		validateStream();
	}

	private void validateStream() {
		DescribeStreamResult result = kinesis.describeStream(streamName);
		String status = result.getStreamDescription().getStreamStatus();
		if (!"ACTIVE".equals(status)) {
			System.err.println("Trader>> ERROR!!!!  Stream " + streamName + " is not active.");
		} else {
			System.out.println("Trader>> STREAM OK");
			processing = true;
		}
	}

	public String create(FakeTrade trade) {
		String kinesisId = "\"-1\"";
		if (processing) {
			byte[] bytes = trade.toJsonAsBytes();
			if (bytes == null) {
				logger.error("Trader>> Could not get JSON bytes for trade: " + trade);
				return kinesisId;
			}
			Random rand = new Random();
			int partitionKey = rand.nextInt();
			ByteBuffer bb = ByteBuffer.wrap(bytes);
			PutRecordResult result = kinesis.putRecord(streamName, bb, partitionKey + "");
			kinesisId = "\"" + result.getShardId() + ":" + result.getSequenceNumber() + "\"";
		}
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

}
