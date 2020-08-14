package com.faketrades.processor;

import org.springframework.stereotype.Component;

import faketrades.domain.FakeTrade;

@Component
public class FakeTradesProcessorDao {

	public void update(FakeTrade ft) {
		System.out.println("FakeTradesProcessorDao>> update " + ft);
	}

	public void create(FakeTrade ft) {
		System.out.println("FakeTradesProcessorDao>> create " + ft);
	}

}
