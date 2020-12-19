package com.ribeiro.restApiCpf.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ribeiro.restApiCpf.model.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Integer>{
	
//	Optional<Person> findByCpf(String cpf);
	
	boolean existsByCpf(String cpf);

}
