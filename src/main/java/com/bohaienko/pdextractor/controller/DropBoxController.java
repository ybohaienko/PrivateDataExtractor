package com.bohaienko.pdextractor.controller;

import com.bohaienko.pdextractor.model.message.AccessToken;
import com.bohaienko.pdextractor.model.message.ResponseMessagesTexts;
import com.bohaienko.pdextractor.model.message.ResponseStatusMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import static com.bohaienko.pdextractor.utils.Commons.timestamp;

@Log4j2
@RestController
@RequestMapping(value = "/dropbox")
class DropBoxController extends BaseController {

	@PostMapping("/start")
	private ResponseStatusMessage start(@RequestBody AccessToken accessToken) {
		thread = new Thread(() -> crawlingHandler.crawlDropbox(accessToken.getToken()));
		try {
			log.info("================== Crawling cycle num: {} ==================", cycleCounter++);
			thread.start();
			log.info("STARTED DropBox crawling process at: {}", timestamp());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ResponseStatusMessage(ResponseMessagesTexts.SUCCESS);
	}

	@PostMapping("/stop")
	private ResponseStatusMessage stop() {
		try {
			thread.interrupt();
			thread.join();
			log.info("STOPPED DropBox crawling process at: {}", timestamp());
		} catch (InterruptedException | NullPointerException ignored) {
		}
		return new ResponseStatusMessage(ResponseMessagesTexts.SUCCESS);
	}

	@GetMapping("/status")
	private ResponseStatusMessage status() {
		boolean status = false;
		try {
			status = thread.isAlive();
			log.debug("REQUESTED DropBox crawling process state: {}", timestamp());
		} catch (NullPointerException ignored) {
		}
		return new ResponseStatusMessage(status ? ResponseMessagesTexts.ACTIVE : ResponseMessagesTexts.STOPPED);
	}
}
