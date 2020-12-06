package com.bohaienko.pdextractor.controller;

import com.bohaienko.pdextractor.model.message.DropBoxToken;
import com.bohaienko.pdextractor.model.message.MessageStatuses;
import com.bohaienko.pdextractor.model.message.ResponseStatusMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.bohaienko.pdextractor.utils.Commons.timestamp;

@Log4j2
@RestController
class DropBoxController extends BaseController {

	@PostMapping("/dropbox/start")
	private ResponseStatusMessage start(@RequestBody DropBoxToken dropBoxToken) {
		thread = new Thread(() -> crawlingHandler.crawlDropbox(dropBoxToken.getToken()));
		try {
			log.info("================== Cycle num: {} ==================", cycleCounter++);
			thread.start();
			log.info("STARTED DropBox crawling process at: {}", timestamp());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ResponseStatusMessage(MessageStatuses.SUCCESS);
	}

	@PostMapping("/dropbox/stop")
	private ResponseStatusMessage stop() {
		try {
			thread.interrupt();
			thread.join();
			log.info("STOPPED DropBox crawling process at: {}", timestamp());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ResponseStatusMessage(MessageStatuses.SUCCESS);
	}
}
