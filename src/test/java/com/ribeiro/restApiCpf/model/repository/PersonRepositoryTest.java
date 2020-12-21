package com.ribeiro.restApiCpf.model.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ribeiro.restApiCpf.model.entity.Person;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class PersonRepositoryTest {

	@Autowired
	PersonRepository repository;
	
	@Autowired
	TestEntityManager testEntityManager;
	
	public static Person createTestPerson() {
		return Person.builder().name("Usuario Teste").cpf("11122233344").build();
	}
	
	@Test
	public void verifyExistsByCpfWhenCpfWasSaved() {
		Person person = createTestPerson();
		testEntityManager.persist(person);
		
		boolean result = repository.existsByCpf("11122233344");
		
		Assertions.assertThat(result).isTrue();
	}
	
	@Test
	public void verifyExistsByCpfIfRetournFalseWhenCpfIsAlreadyRegistered() {
		
		boolean result = repository.existsByCpf("11122233344");
		
		Assertions.assertThat(result).isFalse();
	}
	
	@Test
	public void verifyIfPersonWasSaved() {
		Person person = createTestPerson();
		
		Person personSaved = repository.save(person);
		
		Assertions.assertThat( personSaved.getId() ).isNotNull();
	}
}
