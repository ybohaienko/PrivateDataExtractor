package com.bohaienko.pdextractor.service;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class DdxService {

	@Value("${service.ddx.token}")
	public String ACCESS_TOKEN;

	private static DbxClientV2 client;

	public List<Path> getDdxFilePaths() {
		DbxRequestConfig config = new DbxRequestConfig("DipAcc");
		client = new DbxClientV2(config, ACCESS_TOKEN);

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

	public FileMetadata downloadDdxFiles(Path srcPath, String outputPath) {
		OutputStream downloadFile = null;
		FileMetadata metadata = null;
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
}
