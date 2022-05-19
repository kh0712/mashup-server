package kr.mashup.branding.ui;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {
	@GetMapping("/ping")
	public boolean ping() {
		return true;
	}

	@GetMapping("/v5/ping")
	public boolean pingV5() {
		return true;
	}
}
