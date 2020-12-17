package com.bohaienko.pdextractor.service.client;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Component
public class DropBoxClient {

	private static DbxClientV2 client;

	public List<Path> enableDpxDiscoveryForToken(String accessToken) {
		DbxRequestConfig config = new DbxRequestConfig("DipAcc");
		client = new DbxClientV2(config, accessToken);
		return retrieveFilePaths();
	}

	public FileMetadata downloadDdxFiles(Path srcPath, String outputPath) {
		OutputStream downloadFile = null;
		FileMetadata metadata = null;
		log.info("Downloading discovered file: {}", srcPath);
		try {
			downloadFile = new FileOutputStream(outputPath + srcPath.getFileName().toString());
			metadata = client.files().downloadBuilder(srcPath.toString())
					.download(downloadFile);
		} catch (IOException | DbxException e) {
			e.printStackTrace();
		}
		try {
			if (downloadFile != null) downloadFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return metadata;
	}

	private List<Path> retrieveFilePaths() {
		List<Path> filePaths = null;
		try {
			ListFolderResult result = client.files().listFolderBuilder("").withRecursive(true).start();
			filePaths = new ArrayList<>();
			while (true) {
				for (Metadata metadata : result.getEntries())
					if (metadata instanceof FileMetadata) filePaths.add(Paths.get(metadata.getPathLower()));

				if (!result.getHasMore()) break;
				result = client.files().listFolderContinue(result.getCursor());
			}
		} catch (DbxException e) {
			e.printStackTrace();
		}
		return filePaths;
	}
}
