package com.bohaienko.pdextractor.config;

import com.bohaienko.pdextractor.model.Individual;
import com.bohaienko.pdextractor.model.PrivateDataType;
import com.bohaienko.pdextractor.model.SourceDocument;
import com.bohaienko.pdextractor.model.pdTypeValues.*;
import com.bohaienko.pdextractor.repository.pdTypeValues.BasePdTypeValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GenericInstanceCreator {

	@Autowired
	private RepoInitializer repoInitializer;

	public void saveGeneric(PrivateDataType pdType, String value, SourceDocument doc, Individual individual) {
		Map<PrivateDataType, BasePdTypeValueRepository> repos = repoInitializer.getRepositories();
		try {
			switch (pdType) {
				case TYPE_FIRST_NAME:
					repos.get(pdType).save(new FirstName(value, pdType.name(), doc, individual));
					break;
				case TYPE_SECOND_NAME:
					repos.get(pdType).save(new SecondName(value, pdType.name(), doc, individual));
					break;
				case TYPE_FATHERS_NAME:
					repos.get(pdType).save(new FathersName(value, pdType.name(), doc, individual));
					break;
				case TYPE_ID_TAX_NUMBER:
					repos.get(pdType).save(new IdTaxNumber(value, pdType.name(), doc, individual));
					break;
				case TYPE_PASSPORT_NUMBER:
					repos.get(pdType).save(new PassportNumber(value, pdType.name(), doc, individual));
					break;
				case TYPE_FOREIGN_PASSPORT_NUMBER:
					repos.get(pdType).save(new ForeignPassportNumber(value, pdType.name(), doc, individual));
					break;
				case TYPE_PHONE_NUMBER:
					repos.get(pdType).save(new PhoneNumber(value, pdType.name(), doc, individual));
					break;
				case TYPE_EMAIL:
					repos.get(pdType).save(new Email(value, pdType.name(), doc, individual));
					break;
				case TYPE_WEBSITE:
					repos.get(pdType).save(new Website(value, pdType.name(), doc, individual));
					break;
				case TYPE_ADDRESS_COUNTRY:
					repos.get(pdType).save(new AddressCountry(value, pdType.name(), doc, individual));
					break;
				case TYPE_ADDRESS_CITY:
					repos.get(pdType).save(new AddressCity(value, pdType.name(), doc, individual));
					break;
				case TYPE_ADDRESS_STREET_NUMBER:
					repos.get(pdType).save(new AddressStreetNumber(value, pdType.name(), doc, individual));
					break;
				case TYPE_ADDRESS_APT_NUMBER:
					repos.get(pdType).save(new AddressAptNumber(value, pdType.name(), doc, individual));
					break;
				case TYPE_WORKING_PLACE:
					repos.get(pdType).save(new WorkingPlace(value, pdType.name(), doc, individual));
					break;
				case TYPE_PROFESSION:
					repos.get(pdType).save(new Profession(value, pdType.name(), doc, individual));
					break;
				default:
					break;
			}
		} catch (Exception ignored) {
		}
	}
}
