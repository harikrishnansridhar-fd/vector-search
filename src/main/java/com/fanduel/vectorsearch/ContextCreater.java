package com.fanduel.vectorsearch;

import org.springframework.stereotype.Component;

import com.fanduel.vectorsearch.secondary.entity.PersonDetailsEntity;

public class ContextCreater {

	public static String context( PersonDetailsEntity personDetailsEntity ) {
		return personDetailsEntity.getFirstName() + " " + personDetailsEntity.getLastName() + " " + personDetailsEntity.getAddress() + " " + personDetailsEntity.getCity() + " " + personDetailsEntity.getState() + " " + personDetailsEntity.getZip() + " " + personDetailsEntity.getBirthDate() + " " + personDetailsEntity.getSsn4() + " " + personDetailsEntity.getSsn9();
	}
}
