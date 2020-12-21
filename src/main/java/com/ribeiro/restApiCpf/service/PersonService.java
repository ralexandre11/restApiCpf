package com.ribeiro.restApiCpf.service;

import java.util.List;
import java.util.Optional;

import com.ribeiro.restApiCpf.model.entity.Person;

public interface PersonService {
	
	Person savePerson(Person person);
	
	Person updatePerson(Person person);
	
	void deletePerson(Person person);
	
	List<Person> searchPerson(Person personFilter);
	
	void checkCpf(String cpf);
	
	void validateFields(Person person);
	
	Optional<Person> getPersonByID(Integer id);

}
