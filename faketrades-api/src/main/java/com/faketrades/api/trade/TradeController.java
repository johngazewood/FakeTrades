package com.faketrades.api.trade;

import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trade")
public class TradeController {

	private static final Logger logger = LogManager.getLogger(TradeController.class);
	
//	@PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
	@CrossOrigin
	@PostMapping(path = "/create")
	public String postCreateTrade(HttpServletResponse response) {
		logger.info("please, actually create a trade. request: " + "trade");

		// (grab latest/largest existing tradeid)
		// write to the stream.
		// verify it gets processed. (confirm there is a trade with tradeid > alreadyExistingLargestTradeId from above^^)


		System.out.println(String.format("TradeController>> pretending to create trade %s ....", "trade"));
		// response.addHeader("Access-Control-Allow-Origin", "*");

		Random rand = new Random();
		int x = Math.abs(rand.nextInt());
		String s = String.format("{\"tradeid\":%s}", x);
		System.out.println(String.format("TradeController>> 'new' trade id: %s", s));
		return s;

	}

}
