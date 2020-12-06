package com.bohaienko.pdextractor.service;

import com.bohaienko.pdextractor.service.client.DropBoxClient;
import com.dropbox.core.v2.files.FileMetadata;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Log4j2
@Service
public class CrawlingStarter {

	@Autowired
	DropBoxClient ddxService;

	@Autowired
	PDTypeProcessor pdTypeProcessor;

	@Autowired
	PDProcessor pdProcessor;

	@Autowired
	FileProcessor fileProcessor;

	public void crawlDropbox(String accessToken) {
		File tempDir = new File("temp");
		//noinspection ResultOfMethodCallIgnored
		tempDir.mkdir();
		ddxService.enableDpxDiscoveryForToken(accessToken).forEach(filePath -> {
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
