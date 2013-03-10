package com.encode.borg;

public interface BeanSQLMappings {

	// person
	String PERSON_TABLE_NAME = "person";
	String PERSON_COLUMN_ID = PERSON_TABLE_NAME + "_id";

	// person job
	String PERSON_JOB_TABLE_NAME = "person_job";
	String PERSON_JOB_COLUMN_ID = PERSON_JOB_TABLE_NAME + "_id";
	String PERSON_JOB_COLUMN_PERSON_ID = PERSON_COLUMN_ID;
}
