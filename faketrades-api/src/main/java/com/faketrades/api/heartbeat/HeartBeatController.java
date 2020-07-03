package com.faketrades.api.heartbeat;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/heartbeat")
public class HeartBeatController {

	@GetMapping(path = "", produces = "application/json")
	public String getHeartBeat() {
		return "The heart beats back";
	}

}
