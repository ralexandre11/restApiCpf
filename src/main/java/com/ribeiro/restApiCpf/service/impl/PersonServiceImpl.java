package com.ribeiro.restApiCpf.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ribeiro.restApiCpf.exception.MyRuleException;
import com.ribeiro.restApiCpf.model.entity.Person;
import com.ribeiro.restApiCpf.model.repository.PersonRepository;
import com.ribeiro.restApiCpf.service.PersonService;

@Service
public class PersonServiceImpl implements PersonService {
	
	@Autowired
	PersonKafkaService personKafkaService;
	
	private PersonRepository repository;
	
	public PersonServiceImpl(PersonRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional
	public Person savePerson(Person person) {
		validateFields(person);
		checkCpf(person.getCpf());
		person = repository.save(person);
		personKafkaService.send(person);
		return person;
	}

	@Override
	@Transactional
	public Person updatePerson(Person person) {
		Objects.requireNonNull(person.getId());
		validateFields(person);
		return repository.save(person);
	}

	@Override
	@Transactional
	public void deletePerson(Person person) {
		Objects.requireNonNull(person.getId());
		repository.delete(person);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Person> searchPerson(Person personFilter) {
		Example<Person> example = Example.of(personFilter, 
				ExampleMatcher.matching()
					.withIgnoreCase()
					.withStringMatcher(StringMatcher.CONTAINING));
		return repository.findAll(example);
	}

	@Override
	public void checkCpf(String cpf) {
		boolean existCpf = repository.existsByCpf(cpf);
		if (existCpf) {
			throw new MyRuleException("Já existe uma pessoal cadastrada com este CPF!");
		}
		
	}
	
	public void validateFields(Person person) {
		if(person.getName() == null || person.getName().trim().equals("")) {
			throw new MyRuleException("Informe um Nome válido!");
		}
		if(person.getCpf() == null || person.getCpf().toString().length() != 11) {
			throw new MyRuleException("Informe um CPF válido com 11 dígitos!");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Person> getPersonByID(Integer id) {
		return repository.findById(id);
	}

}
