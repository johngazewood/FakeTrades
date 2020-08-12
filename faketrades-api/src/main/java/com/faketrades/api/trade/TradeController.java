package com.faketrades.api.trade;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.faketrades.domain.FakeTrade;
import com.google.gson.Gson;

@RestController
@RequestMapping("/trade")
public class TradeController {

	private static final Logger logger = LogManager.getLogger(TradeController.class);
	
	@Autowired
	Trader trader;

	@CrossOrigin
	@PostMapping(path = "/create")
	public String postCreateTrade(@RequestBody FakeTrade trade) {
		logger.info("please, actually create a trade. request: " + trade);

		// (grab latest/largest existing tradeid)
		// write to the stream.
		// verify it gets processed. (confirm there is a trade with tradeid >
		// alreadyExistingLargestTradeId from above^^)

		String id = trader.create(trade);

		String s = String.format("{\"kinesisid\":%s}", id);
		System.out.println(String.format("TradeController>> api responding with %s", s));
		return s;

	}

	@CrossOrigin
	@GetMapping(path = "/view")
	public String viewTrades() {
		List<FakeTrade> trades = new LinkedList<FakeTrade>();
		for (int i = 1; i < 1000; i++) {
			FakeTrade t1 = new FakeTrade();
			t1.setTradeId(i);
			t1.setAmount(new BigDecimal(i + "000000"));
			trades.add(t1);
		}

		return new Gson().toJson(trades);
	}
	
}
