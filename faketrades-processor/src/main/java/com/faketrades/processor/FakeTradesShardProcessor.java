package com.faketrades.processor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import faketrades.domain.FakeTrade;
import software.amazon.kinesis.exceptions.InvalidStateException;
import software.amazon.kinesis.exceptions.KinesisClientLibDependencyException;
import software.amazon.kinesis.exceptions.ShutdownException;
import software.amazon.kinesis.exceptions.ThrottlingException;
import software.amazon.kinesis.lifecycle.events.InitializationInput;
import software.amazon.kinesis.lifecycle.events.LeaseLostInput;
import software.amazon.kinesis.lifecycle.events.ProcessRecordsInput;
import software.amazon.kinesis.lifecycle.events.ShardEndedInput;
import software.amazon.kinesis.lifecycle.events.ShutdownRequestedInput;
import software.amazon.kinesis.processor.ShardRecordProcessor;
import software.amazon.kinesis.retrieval.KinesisClientRecord;

public class FakeTradesShardProcessor implements ShardRecordProcessor {

	private static final Logger logger = LogManager.getLogger(FakeTradesShardProcessor.class);

	private String shardId;

	FakeTradesProcessorDao dao;

	public FakeTradesShardProcessor(FakeTradesProcessorDao dao) {
		this.dao = dao;
	}

	@Override
	public void initialize(InitializationInput initializationInput) {
		shardId = initializationInput.shardId();
		logger.info(String.format("Shard %s Initializing @ Sequence: %s", shardId,
				initializationInput.extendedSequenceNumber()));

	}

	@Override
	public void processRecords(ProcessRecordsInput processRecordsInput) {
		processRecordsInput.records().forEach(r -> this.process(r));

	}

	private void process(KinesisClientRecord r) {
		FakeTrade ft = readTrade(r);
		boolean exists = false;
		if (exists) {
			this.dao.update(ft);
		} else {
			this.dao.create(ft);
		}
	}

	private FakeTrade readTrade(KinesisClientRecord r) {
		final byte[] bytes = new byte[r.data().remaining()];
		r.data().duplicate().get(bytes);
		String s = new String(bytes);
		ObjectMapper om = new ObjectMapper();
		FakeTrade ft = null;
		try {
			ft = om.readValue(s, FakeTrade.class);
		} catch (JsonProcessingException e) {
			logger.error("Failed to deserialize FakeTrade: " + e.getMessage());
			e.printStackTrace();
		}
		System.out.println("FakeTradesShardProcessor>> deserialized fake trade: amount=" + ft.getAmount());
		return ft;
	}

	@Override
	public void leaseLost(LeaseLostInput leaseLostInput) {
		logger.info("Lost lease, so terminating.");
	}

	@Override
	public void shardEnded(ShardEndedInput shardEndedInput) {
		try {
			logger.info("Reached shard end checkpointing.");
			shardEndedInput.checkpointer().checkpoint();
		} catch (KinesisClientLibDependencyException | InvalidStateException | ThrottlingException
				| ShutdownException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void shutdownRequested(ShutdownRequestedInput shutdownRequestedInput) {
		try {
			logger.info("Scheduler is shutting down, checkpointing.");
			shutdownRequestedInput.checkpointer().checkpoint();
		} catch (ShutdownException | InvalidStateException e) {
			logger.error("Exception while checkpointing at requested shutdown. Giving up.", e);
		}
	}

	public FakeTradesProcessorDao getDao() {
		return dao;
	}

	public void setDao(FakeTradesProcessorDao dao) {
		this.dao = dao;
	}

}
