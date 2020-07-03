package faketrades.test.automation.kinesis;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Component;

import faketrades.domain.FakeTrade;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.kinesis.KinesisAsyncClient;
import software.amazon.awssdk.services.kinesis.model.DescribeStreamRequest;
import software.amazon.awssdk.services.kinesis.model.DescribeStreamResponse;
import software.amazon.awssdk.services.kinesis.model.PutRecordRequest;
import software.amazon.awssdk.services.kinesis.model.PutRecordResponse;
import software.amazon.kinesis.common.KinesisClientUtil;

@Component
public class KinesisWriter {

	String shardId = "shardId-000000000000";
	String streamName = "FakeTradesStream";
	String shardIteratorType = "TRIM_HORIZON";
	Region region = Region.of("us-east-2");
	KinesisAsyncClient kinesisClient;

	public static void main(String args[]) {
		FakeTrade trade = new FakeTrade();
		trade.setAmount(new BigDecimal("30000"));
		trade.setTradeId(3);
		KinesisWriter kinesisWriter = new KinesisWriter();
		kinesisWriter.sendFakeTrade(trade);
	}

	public KinesisWriter() {
		kinesisClient = KinesisClientUtil.createKinesisAsyncClient(KinesisAsyncClient.builder().region(region));
		validateStream(kinesisClient, streamName);

	}

	private static void validateStream(KinesisAsyncClient kinesisClient, String streamName) {
		try {
			DescribeStreamRequest describeStreamRequest = DescribeStreamRequest.builder().streamName(streamName)
					.build();
			DescribeStreamResponse describeStreamResponse = kinesisClient.describeStream(describeStreamRequest).get();
			if (!describeStreamResponse.streamDescription().streamStatus().toString().equals("ACTIVE")) {
				System.err.println("Stream " + streamName + " is not active. Please wait a few moments and try again.");
				System.exit(1);
			} else {
				System.out.println(
						"KinesisWriter>> ----------------------------------------------------------------- ACTIVE");
			}
		} catch (Exception e) {
			System.err.println("Error found while describing the stream " + streamName);
			System.err.println(e);
			System.exit(1);
		}
	}

	public void sendFakeTrade(FakeTrade trade) {
		byte[] bytes = trade.toJsonAsBytes();
		if (bytes == null) {
			System.err.println("Could not get JSON bytes for trade: " + trade);
			return;
		}

		System.out.println("Putting trade: " + trade);
//		PutRecordRequest request = PutRecordRequest.builder().partitionKey(trade.getTradeId() + "")
//				.streamName(streamName).data(SdkBytes.fromByteArray(bytes)).build();
		PutRecordRequest request = PutRecordRequest.builder().partitionKey(trade.getTradeId() + "")
				.streamName(streamName).data(SdkBytes.fromByteArray(bytes)).build();
		CompletableFuture<PutRecordResponse> cfprr = kinesisClient.putRecord(request);
		PutRecordResponse response = null;
		try {
			response = cfprr.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		System.out.println("KinesisWriter>> response: " + response.toString());
	}

	public void dummyMethod() {
		System.out.println("KinesisWriter>> you successfully ran a method in KinesisWriter.");
	}

}
