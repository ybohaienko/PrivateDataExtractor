package com.bohaienko.pdextractor.config;

import com.bohaienko.pdextractor.repository.ColumnPersistenceDataRepository;
import com.bohaienko.pdextractor.repository.DocumentPersistenceDataRepository;
import com.bohaienko.pdextractor.repository.individual.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	CommandLineRunner initDatabase(
			DocumentPersistenceDataRepository documentPersistenceDataRepository,
			ColumnPersistenceDataRepository columnPersistenceDataRepository,
			AddressCityRepository addressCityRepository,
			AddressCountryRepository addressCountryRepository,
			AddressStreetNumberRepository addressStreetNumberRepository,
			EmailRepository emailRepository,
			FathersNameRepository fathersNameRepository,
			FirstNameRepository firstNameRepository,
			ForeignPassportNumberRepository foreignPassportNumberRepository,
			IdTaxNumberRepository idTaxNumberRepository,
			PassportNumberRepository passportNumberRepository,
			PhoneNumberRepository phoneNumberRepository,
			ProfessionRepository professionRepository,
			SecondNameRepository secondNameRepository,
			WebsiteRepository websiteRepository,
			WorkingPlaceRepository workingPlaceRepository
	) {
		return args -> {
		};
	}
}
