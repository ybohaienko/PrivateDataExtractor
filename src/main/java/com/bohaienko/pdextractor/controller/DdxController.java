package com.bohaienko.pdextractor.controller;

import com.bohaienko.pdextractor.model.message.DdxToken;
import org.springframework.web.bind.annotation.*;

@RestController
class DdxController {
	private Thread thread1;

	@PostMapping("/crawlDdx")
	void create(@RequestBody DdxToken ddxToken) {
		thread1 = new Thread(

		);
		thread1.start();
	}

	@DeleteMapping("/crawlDdx")
	void delete() {
		thread1.stop();
	}
}
