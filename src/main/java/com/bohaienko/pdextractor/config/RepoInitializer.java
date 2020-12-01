package com.bohaienko.pdextractor.config;

import com.bohaienko.pdextractor.model.PrivateDataType;
import com.bohaienko.pdextractor.repository.individual.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashMap;
import java.util.Map;

public class RepoInitializer {
	@Autowired
	private AddressAptNumberRepository addressAptNumberRepository;

	@Autowired
	private AddressCityRepository addressCityRepository;

	@Autowired
	private AddressCountryRepository addressCountryRepository;

	@Autowired
	private AddressStreetNumberRepository addressStreetNumberRepository;

	@Autowired
	private EmailRepository emailRepository;

	@Autowired
	private FathersNameRepository fathersNameRepository;

	@Autowired
	private FirstNameRepository firstNameRepository;

	@Autowired
	private ForeignPassportNumberRepository foreignPassportNumberRepository;

	@Autowired
	private IdTaxNumberRepository idTaxNumberRepository;

	@Autowired
	private PassportNumberRepository passportNumberRepository;

	@Autowired
	private PhoneNumberRepository phoneNumberRepository;

	@Autowired
	private ProfessionRepository professionRepository;

	@Autowired
	private SecondNameRepository secondNameRepository;

	@Autowired
	private WebsiteRepository websiteRepository;

	@Autowired
	private WorkingPlaceRepository workingPlaceRepository;

	public Map<PrivateDataType, JpaRepository> getRepositories() {
		Map<PrivateDataType, JpaRepository> map = new HashMap<>();
		map.put(PrivateDataType.TYPE_FIRST_NAME, firstNameRepository);
		map.put(PrivateDataType.TYPE_SECOND_NAME, secondNameRepository);
		map.put(PrivateDataType.TYPE_FATHERS_NAME, fathersNameRepository);
		map.put(PrivateDataType.TYPE_ID_TAX_NUMBER, idTaxNumberRepository);
		map.put(PrivateDataType.TYPE_PASSPORT_NUMBER, passportNumberRepository);
		map.put(PrivateDataType.TYPE_FOREIGN_PASSPORT_NUMBER, foreignPassportNumberRepository);
		map.put(PrivateDataType.TYPE_PHONE_NUMBER, phoneNumberRepository);
		map.put(PrivateDataType.TYPE_EMAIL, emailRepository);
		map.put(PrivateDataType.TYPE_WEBSITE, websiteRepository);
		map.put(PrivateDataType.TYPE_ADDRESS_COUNTRY, addressCountryRepository);
		map.put(PrivateDataType.TYPE_ADDRESS_CITY, addressCityRepository);
		map.put(PrivateDataType.TYPE_ADDRESS_STREET_NUMBER, addressStreetNumberRepository);
		map.put(PrivateDataType.TYPE_ADDRESS_APT_NUMBER, addressAptNumberRepository);
		map.put(PrivateDataType.TYPE_WORKING_PLACE, workingPlaceRepository);
		map.put(PrivateDataType.TYPE_PROFESSION, professionRepository);
		return map;
	}
}
