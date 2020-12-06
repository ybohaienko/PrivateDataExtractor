package com.bohaienko.pdextractor;

import com.bohaienko.pdextractor.repository.DocumentPersistenceDataRepository;
import com.bohaienko.pdextractor.repository.individual.*;
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

	@Autowired
	private FathersNameRepository fathersNameRepository;
	@Autowired
	private SecondNameRepository secondNameRepository;
	@Autowired
	private FirstNameRepository firstNameRepository;
	@Autowired
	private PhoneNumberRepository phoneNumberRepository;
	@Autowired
	private IndividualRepository individualRepository;
	@Autowired
	private DocumentPersistenceDataRepository documentRepository;


	public static void main(String[] args) {
		SpringApplication.run(PdExtractorApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void some() {
		File tempDir = new File("temp");
		tempDir.mkdir();
		ddxService.getDdxFilePaths().forEach(filePath -> {
			FileMetadata metadata = ddxService.downloadDdxFiles(filePath, tempDir.getPath() + File.separator);

			String tempLocalFilePath = tempDir.getPath() + File.separator + metadata.getName();
			int pdTypeProcessorFileLines = 10;
			Long docId = pdTypeProcessor.processColumnsType(tempLocalFilePath, pdTypeProcessorFileLines, metadata.getPathLower());

			pdProcessor.process(
					fileProcessor.retrievePayloadFromFileByDocument(tempLocalFilePath, docId));

//			new File(tempLocalFilePath).delete();
		});
//		tempDir.delete();

//		pdTypeProcessor.processColumnType("temp/" + "rec_test.csv", 0, "somePath");

//		Individual individual = individualRepository.save(new Individual(UUID.randomUUID()));
//		Individual individual2 = individualRepository.save(new Individual(UUID.randomUUID()));
//		DocumentPersistenceData doc = documentRepository.save(new DocumentPersistenceData("some2.file", "/root/"));
//		DocumentPersistenceData doc2 = documentRepository.save(new DocumentPersistenceData("some.file", "/root/"));
//
//		fathersNameRepository.save(new FathersName("Ivanovych", doc, individual));
//		firstNameRepository.save(new FirstName("Ivan", doc, individual));
//
//		fathersNameRepository.save(new FathersName("Petrovych", doc2, individual2));
//		secondNameRepository.save(new SecondName("Melnyk", doc2, individual2));
//		firstNameRepository.save(new FirstName("Petro", doc2, individual2));
//		phoneNumberRepository.save(new PhoneNumber("+380501234567", doc2, individual2));
//
//		firstNameRepository.save(new FirstName("some", doc, individual));
//
//		pdProcessor.process(pdProcessor.createDummy());

//		try {
//			Class<?> clazz = Class.forName(FirstName.class.getName());
//			Constructor<?> ctor = clazz.getConstructor(String.class, DocumentPersistenceData.class, Individual.class);
//			firstNameRepository.save(ctor.newInstance("some", doc, individual));
//		} catch (Exception e) {
//		}
	}
}
