package com.faketrades.api.heartbeat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/heartbeat")
public class HeartBeatController {

	private static final Logger logger = LogManager.getLogger(HeartBeatController.class);

	@GetMapping(path = "", produces = "application/json")
	public String getHeartBeat() {
		logger.info("GET /heartbeat requested. -- this is a small log edit.");
		return "The heart beats back";
	}

}
