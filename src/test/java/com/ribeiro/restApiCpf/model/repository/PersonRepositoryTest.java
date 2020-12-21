package com.ribeiro.restApiCpf.model.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ribeiro.restApiCpf.model.entity.Person;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class PersonRepositoryTest {

	@Autowired
	PersonRepository repository;
	
	@Test
	public void verifyIfExistsCpfWhenCpfWasSaved() {
		Person person = Person.builder().name("Usuario Teste").cpf("11122233344").build();
		repository.save(person);
		
		boolean result = repository.existsByCpf("11122233344");
		
		Assertions.assertThat(result).isTrue();
	}
	
	@Test
	public void verifyIfRetournFalseWhenCpfIsAlreadyRegistered() {
		repository.deleteAll();
		
		boolean result = repository.existsByCpf("11122233344");
		
		Assertions.assertThat(result).isFalse();
	}
	
}
