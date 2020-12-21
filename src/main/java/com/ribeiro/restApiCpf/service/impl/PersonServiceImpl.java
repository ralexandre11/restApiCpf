package com.ribeiro.restApiCpf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ribeiro.restApiCpf.exception.MyRuleException;
import com.ribeiro.restApiCpf.model.entity.Person;
import com.ribeiro.restApiCpf.model.repository.PersonRepository;
import com.ribeiro.restApiCpf.service.PersonService;

@Service
public class PersonServiceImpl implements PersonService {
	
	private PersonRepository repository;
	
	@Autowired
	public PersonServiceImpl(PersonRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	@Transactional
	public Person savePerson(Person person) {
		checkCpf(person.getCpf());
		return repository.save(person);
	}

	@Override
	public void checkCpf(String cpf) {
		boolean existCpf = repository.existsByCpf(cpf);
		if (existCpf) {
			throw new MyRuleException("Já existe uma pessoal cadastrada com este CPF!");
		}
		
	}

}
