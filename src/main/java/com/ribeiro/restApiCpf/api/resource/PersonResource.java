package com.ribeiro.restApiCpf.api.resource;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ribeiro.restApiCpf.api.dto.PersonDTO;
import com.ribeiro.restApiCpf.exception.MyRuleException;
import com.ribeiro.restApiCpf.model.entity.Person;
import com.ribeiro.restApiCpf.service.PersonService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/person")
@RequiredArgsConstructor
public class PersonResource {

	private final PersonService service;
	
	@GetMapping
	public ResponseEntity<List<Person>> search(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "cpf", required = false) String cpf
			) {
		
		Person personFilter = new Person(name, cpf);
		List<Person> personList = service.search(personFilter);
		return ResponseEntity.ok(personList);
	}
	

	@PostMapping
	public ResponseEntity save( @RequestBody PersonDTO dto ) {
		try {
			Person person = convert(dto);
			Person personSaved = service.savePerson(person);
			return new ResponseEntity(personSaved, HttpStatus.CREATED);
		} catch (MyRuleException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("{id}")
	public ResponseEntity update( @PathVariable("id") Integer id, @RequestBody PersonDTO dto) {
		return service.getPersonByID(id).map(entity -> {
			try {
				Person person = convert(dto);
				person.setId(entity.getId());
				Person personSaved = service.updatePerson(person);
				System.out.println(personSaved);
				return ResponseEntity.ok(person);
			} catch (Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet( () ->
			new ResponseEntity("Registro inexistente!", HttpStatus.BAD_REQUEST ));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity delete( @PathVariable("id") Integer id) {
		return service.getPersonByID(id).map( entity -> {
			service.deletePerson(entity);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}).orElseGet( () -> 
			new ResponseEntity("Registro inexistente!", HttpStatus.BAD_REQUEST ));
	}
	
	
	private Person convert(PersonDTO dto) {
		Person person = new Person();
		person.setName(dto.getName());
		person.setCpf(dto.getCpf());
		
		return person;
	}
}
