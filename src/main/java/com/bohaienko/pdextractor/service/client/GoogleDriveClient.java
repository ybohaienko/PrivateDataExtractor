package com.bohaienko.pdextractor.service.client;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Service
public class GoogleDriveClient {
	@Value("${service.gdv.root.folder}")
	private String rootFolderId = "1wpI90uLF-9LknHzDqsp2IhaK11QjkRjG";

	private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static final String TOKENS_DIRECTORY_PATH = "tokens";
	private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE_METADATA_READONLY);
	private static final String CREDENTIALS_FILE_PATH = "/client_secret.json";
	private Drive client;

	private void enableClient() {
		final NetHttpTransport HTTP_TRANSPORT;
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			client = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
					.setApplicationName(APPLICATION_NAME)
					.build();
		} catch (GeneralSecurityException | IOException e) {
			e.printStackTrace();
		}
	}

	public FileList getListOfFiles() throws IOException {
		return client.files().list()
				.setQ("mimeType != 'application/vnd.google-apps.folder' " +
						"and trashed = false " +
						"and parents in '" + rootFolderId + "'")
				.setSpaces("drive")
				.setFields("nextPageToken, files(id, name, parents)")
				.setPageSize(100)
				.setPageToken(null)
				.execute();
	}

	public static void main(String... args) throws IOException {
		GoogleDriveClient lol = new GoogleDriveClient();
		lol.enableClient();
		lol.getListOfFiles().getFiles().forEach(f -> {
			try {
				lol.downloadFile(f.getId());
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});

//		if (result != null) {
//			for (File file : result.getFiles()) {
//				System.out.println("Title: " + file.getName());
//				System.out.println("Description: " + file.getId());
//				System.out.println("MIME type: " + file.getFileExtension());
//			}
//		}
	}

	public void downloadFile(String fileId) throws IOException {
		OutputStream outputStream = new ByteArrayOutputStream();
		client.files().export(fileId, "application/pdf")
				.executeMediaAndDownloadTo(outputStream);
	}

	private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
		InputStream in = GoogleDriveClient.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
		if (in == null)
			throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
				.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
				.setAccessType("offline")
				.build();
		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
		return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
	}
}
