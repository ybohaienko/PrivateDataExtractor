package com.bohaienko.pdextractor.model;

public enum PrivateDataType {
	TYPE_FIRST_NAME("typeFirstName"),
	TYPE_SECOND_NAME("typeSecondName"),
	TYPE_FATHERS_NAME("typeFathersName"),
	TYPE_ID_TAX_NUMBER("typeIdTaxNumber"),
	TYPE_PASSPORT_NUMBER("typePassportNumber"),
	TYPE_FOREIGN_PASSPORT_NUMBER("typeForeignPassportNumber"),
	TYPE_PHONE_NUMBER("typePhoneNumber"),
	TYPE_EMAIL("typeEmail"),
	TYPE_WEBSITE("typeWebsite"),
	TYPE_ADDRESS_COUNTRY("typeAddressCountry"),
	TYPE_ADDRESS_CITY("typeAddressCity"),
	TYPE_ADDRESS_STREET_NUMBER("typeAddressStreetNumber"),
	TYPE_ADDRESS_APT_NUMBER("typeAddressAptNumber"),
	TYPE_WORKING_PLACE("typeWorkingPlace"),
	TYPE_PROFESSION("typeProfession"),
	TYPE_NONE("none");

	private final String value;

	PrivateDataType(String s) {
		value = s;
	}

	public String value() {
		return value;
	}

	public static PrivateDataType[][] getUnique() {
		return new PrivateDataType[][]{
				{TYPE_ID_TAX_NUMBER},
				{TYPE_PASSPORT_NUMBER},
				{TYPE_FIRST_NAME, TYPE_SECOND_NAME, TYPE_FATHERS_NAME, TYPE_PHONE_NUMBER},
				{TYPE_FIRST_NAME, TYPE_SECOND_NAME, TYPE_FATHERS_NAME, TYPE_FOREIGN_PASSPORT_NUMBER},
		};
	}

	public static PrivateDataType[] getIndividuals() {
		return new PrivateDataType[]{TYPE_FIRST_NAME, TYPE_SECOND_NAME, TYPE_FATHERS_NAME};
	}

	public static PrivateDataType[] getAddresses() {
		return new PrivateDataType[]{TYPE_ADDRESS_COUNTRY, TYPE_ADDRESS_CITY, TYPE_ADDRESS_STREET_NUMBER, TYPE_ADDRESS_APT_NUMBER};
	}

	public static PrivateDataType[] getEmails() {
		return new PrivateDataType[]{TYPE_EMAIL};
	}

	public static PrivateDataType[] getForeignPassports() {
		return new PrivateDataType[]{TYPE_FOREIGN_PASSPORT_NUMBER};
	}

	public static PrivateDataType[] getPhoneNumbers() {
		return new PrivateDataType[]{TYPE_PHONE_NUMBER};
	}

	public static PrivateDataType[] getProfessions() {
		return new PrivateDataType[]{TYPE_PROFESSION};
	}

	public static PrivateDataType[] getWebsites() {
		return new PrivateDataType[]{TYPE_WEBSITE};
	}

	public static PrivateDataType[] getWorkplaces() {
		return new PrivateDataType[]{TYPE_WORKING_PLACE};
	}
}
