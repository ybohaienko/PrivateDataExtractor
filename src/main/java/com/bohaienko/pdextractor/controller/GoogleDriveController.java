package com.bohaienko.pdextractor.controller;

import com.bohaienko.pdextractor.model.message.AccessToken;
import com.bohaienko.pdextractor.model.message.ResponseMessagesTexts;
import com.bohaienko.pdextractor.model.message.ResponseStatusMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping(value = "/gdrive")
class GoogleDriveController extends BaseController {

	@PostMapping("/start")
	private ResponseStatusMessage start(@RequestBody AccessToken accessToken) {
		return new ResponseStatusMessage(ResponseMessagesTexts.SUCCESS);
	}

	@PostMapping("/stop")
	private ResponseStatusMessage stop() {
		return new ResponseStatusMessage(ResponseMessagesTexts.SUCCESS);
	}

	@GetMapping("/status")
	private ResponseStatusMessage status() {
		return new ResponseStatusMessage(ResponseMessagesTexts.STOPPED);
	}
}
