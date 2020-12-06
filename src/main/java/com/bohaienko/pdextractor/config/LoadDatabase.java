package com.bohaienko.pdextractor.config;

import com.bohaienko.pdextractor.repository.ColumnsPersistenceDataRepository;
import com.bohaienko.pdextractor.repository.DocumentPersistenceDataRepository;
import com.bohaienko.pdextractor.repository.individual.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
class LoadDatabase {
	@Bean
	CommandLineRunner initDatabase(
			DocumentPersistenceDataRepository documentPersistenceDataRepository,
			ColumnsPersistenceDataRepository columnPersistenceDataRepository,
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
