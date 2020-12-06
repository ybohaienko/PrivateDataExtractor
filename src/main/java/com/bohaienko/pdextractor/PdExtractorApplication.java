package com.bohaienko.pdextractor;

import com.bohaienko.pdextractor.service.FileProcessor;
import com.bohaienko.pdextractor.service.PDProcessor;
import com.bohaienko.pdextractor.service.PDTypeProcessor;
import com.bohaienko.pdextractor.service.client.DdxClient;
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
	DdxClient ddxService;

	@Autowired
	PDTypeProcessor pdTypeProcessor;

	@Autowired
	PDProcessor pdProcessor;

	@Autowired
	FileProcessor fileProcessor;

	public static void main(String[] args) {
		SpringApplication.run(PdExtractorApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void some() {
		File tempDir = new File("temp");
		//noinspection ResultOfMethodCallIgnored
		tempDir.mkdir();
		ddxService.getDdxFilePaths().forEach(filePath -> {
			FileMetadata metadata = ddxService.downloadDdxFiles(filePath, tempDir.getPath() + File.separator);

			String tempLocalFilePath = tempDir.getPath() + File.separator + metadata.getName();
			int pdTypeProcessorFileLines = 10;
			Long docId = pdTypeProcessor.processColumnsType(tempLocalFilePath, pdTypeProcessorFileLines, metadata.getPathLower());

			pdProcessor.process(
					fileProcessor.retrievePayloadFromFileByDocument(tempLocalFilePath, docId));

			//noinspection ResultOfMethodCallIgnored
			new File(tempLocalFilePath).delete();
		});
		//noinspection ResultOfMethodCallIgnored
		tempDir.delete();
	}
}
