package com.bohaienko.pdextractor;

import com.bohaienko.pdextractor.service.DdxService;
import com.bohaienko.pdextractor.service.PDProcessor;
import com.bohaienko.pdextractor.service.PDTypeRecognizer;
import com.dropbox.core.v2.files.FileMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.io.File;

@SpringBootApplication
public class PdExtractorApplication {

	@Autowired
	DdxService ddxService;

	@Autowired
	PDTypeRecognizer pdTypeProcessor;

	@Autowired
	PDProcessor pdProcessor;

	public static void main(String[] args) {
		SpringApplication.run(PdExtractorApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void some() {
		File f = new File("temp");
//		f.mkdir();
//		ddxService.getDdxFilePaths().forEach(e -> {
//			FileMetadata metadata = ddxService.downloadDdxFiles(e, f.getPath() + File.separator);
//			pdTypeProcessor.processColumnType(f.getPath() + File.separator + metadata.getName(), 0, metadata.getPathLower());
//		});
//		f.delete();

//		pdTypeProcessor.processColumnType("temp/" + "rec_test.csv", 0, "somePath");


		pdProcessor.process(pdProcessor.createDummy());
	}
}
