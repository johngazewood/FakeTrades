package com.faketrades.api.heartbeat;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/heartbeat")
public class HeartBeatController {

	private static final Logger logger = LogManager.getLogger(HeartBeatController.class);

	@GetMapping
	public String getHeartBeat(HttpServletResponse response) {
		logger.info("GET /heartbeat requested.");
		// why wouldn't @CrossOrigin do this.....
		// response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");

		return "{\"heartbeat\":\"The heart beats back. Thump.\"}";
	}

}
