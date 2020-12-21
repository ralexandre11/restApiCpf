package com.ribeiro.restApiCpf.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.checkerframework.checker.fenum.qual.AwtAlphaCompositingRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ribeiro.restApiCpf.model.entity.Person;
import com.ribeiro.restApiCpf.model.repository.PersonRepository;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class PersonServiceTest {

	@Autowired
	PersonService service;
	
	@Autowired
	PersonRepository repository;
	
	@Test()
	public void CheckCpfAndReturnNoException() {
		repository.deleteAll();
		boolean thrown = false;
		
		try {
			service.checkCpf("11122233344");
		} catch (Exception e) {
			thrown = true;
		}
		
		assertFalse(thrown);
	}
	
	@Test()
	public void CheckCpfAndReturnException() {
		Person person = Person.builder().name("Usuario Teste").cpf("11122233344").build();
		repository.save(person);
		boolean thrown = false;

		try {
			service.checkCpf("11122233344");
		} catch (Exception e) {
			thrown = true;
		}

		assertTrue(thrown);
	}

}
