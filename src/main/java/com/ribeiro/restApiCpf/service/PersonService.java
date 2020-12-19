package com.ribeiro.restApiCpf.service;

import com.ribeiro.restApiCpf.model.entity.Person;

public interface PersonService {
	
	Person savePerson(Person person);
	
	void checkCpf(String cpf);

}
