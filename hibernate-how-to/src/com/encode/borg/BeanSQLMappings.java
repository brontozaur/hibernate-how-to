package com.encode.borg;

public interface BeanSQLMappings {

	String ID = "_id";

	// person
	String PERSON_TABLE_NAME = "person";
	String PERSON_COLUMN_ID = PERSON_TABLE_NAME + ID;

	// person job
	String PERSON_JOB_TABLE_NAME = "person_job";
	String PERSON_JOB_COLUMN_ID = PERSON_JOB_TABLE_NAME + ID;
	String PERSON_JOB_COLUMN_PERSON_ID = PERSON_COLUMN_ID;

	// person relative

	String PERSON_RELATIVE_TABLE_NAME = "person_relative";
	String PERSON_RELATIVE_COLUMN_ID = PERSON_RELATIVE_TABLE_NAME + ID;
	String PERSON_RELATIVE_COLUMN_PERSON_ID = PERSON_COLUMN_ID;
}
