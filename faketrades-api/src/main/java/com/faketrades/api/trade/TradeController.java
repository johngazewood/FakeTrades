package com.faketrades.api.trade;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.faketrades.domain.FakeTrade;

@RestController
@RequestMapping("/trade")
public class TradeController {

	private static final Logger logger = LogManager.getLogger(TradeController.class);
	
	@CrossOrigin
	@PostMapping(path = "/create")
	public String postCreateTrade(@RequestBody FakeTrade trade) {
		logger.info("please, actually create a trade. request: " + trade);

		// (grab latest/largest existing tradeid)
		// write to the stream.
		// verify it gets processed. (confirm there is a trade with tradeid > alreadyExistingLargestTradeId from above^^)

		Random rand = new Random();
		int x = Math.abs(rand.nextInt());
		String s = String.format("{\"tradeid\":%s}", x);
		System.out.println(String.format("TradeController>> 'new' trade id: %s", s));
		return s;

	}

}
