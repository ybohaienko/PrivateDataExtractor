package com.bohaienko.pdextractor.service;

import com.bohaienko.pdextractor.service.client.DropBoxClient;
import com.dropbox.core.v2.files.FileMetadata;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

import static com.bohaienko.pdextractor.utils.Commons.timestamp;

@Log4j2
@Service
public class CrawlingHandler {

	@Autowired
	DropBoxClient ddxService;

	@Autowired
	PdTypeProcessor pdTypeProcessor;

	@Autowired
	PdProcessor pdProcessor;

	@Autowired
	FileProcessor fileProcessor;

	public void crawlDropbox(String accessToken) {
		File tempDir = new File("temp");
		log.info("Created temporary directory: {}", tempDir.getPath());
		//noinspection ResultOfMethodCallIgnored
		tempDir.mkdir();
		ddxService.enableDpxDiscoveryForToken(accessToken).forEach(filePath -> {
			FileMetadata metadata = ddxService.downloadFiles(filePath, tempDir.getPath() + File.separator);

			String tempLocalFilePath = tempDir.getPath() + File.separator + metadata.getName();
			int pdTypeProcessorFileLines = 10;
			Long docId = pdTypeProcessor.processColumnsType(tempLocalFilePath, pdTypeProcessorFileLines, metadata.getPathLower());

			pdProcessor.process(
					fileProcessor.retrievePayloadFromFileByDocument(tempLocalFilePath, docId));
			log.info("FINISHED DropBox crawling process at: {}", timestamp());
			//noinspection ResultOfMethodCallIgnored
			new File(tempLocalFilePath).delete();
		});
		//noinspection ResultOfMethodCallIgnored
		tempDir.delete();
		log.info("Cleanup process executed");
	}
}
