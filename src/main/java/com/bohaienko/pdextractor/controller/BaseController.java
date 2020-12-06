package com.bohaienko.pdextractor.controller;

import com.bohaienko.pdextractor.service.CrawlingHandler;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {
	@Autowired
	protected CrawlingHandler crawlingHandler;

	Thread thread;
	int cycleCounter = 1;
}
