package com.bohaienko.pdextractor.controller;

import com.bohaienko.pdextractor.model.message.DropBoxToken;
import com.bohaienko.pdextractor.model.message.ResponseMessagesTexts;
import com.bohaienko.pdextractor.model.message.ResponseStatusMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.bohaienko.pdextractor.utils.Commons.timestamp;

@Log4j2
@RestController
@RequestMapping(value = "/dropbox")
class DropBoxController extends BaseController {

	@PostMapping("/start")
	private ResponseStatusMessage start(@RequestBody DropBoxToken dropBoxToken) {
		thread = new Thread(() -> crawlingHandler.crawlDropbox(dropBoxToken.getToken()));
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

	@PostMapping("/status")
	private ResponseStatusMessage status() {
		boolean status = false;
		try {
			status = thread.isAlive();
			log.info("REQUESTED DropBox crawling process state: {}", timestamp());
		} catch (NullPointerException ignored) {
		}
		return new ResponseStatusMessage(status ? ResponseMessagesTexts.ACTIVE : ResponseMessagesTexts.STOPPED);
	}
}
