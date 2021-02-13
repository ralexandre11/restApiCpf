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
@RequestMapping("/persons")
@RequiredArgsConstructor
public class PersonResource {

	private final PersonService service;
	
	@GetMapping
  public ResponseEntity<List<Person>> search(
			@RequestParam(value = "name", required = false) final String name,
			@RequestParam(value = "cpf", required = false) final String cpf
			) {
		
    final Person personFilter = new Person(null, name, cpf);
    // TODO rename to service.search
		final List<Person> personList = service.searchPerson(personFilter);
		return ResponseEntity.ok(personList);
	}
	

	@PostMapping
  // TODO PersonDTO deve user usado em toda parte no Resource (o resource usualmente não deve
  // conhecer o objeto de negócio, apenas o DTO)
  public ResponseEntity save(@RequestBody final PersonDTO dto)
  {
		try {
      // TODO deve ser feito na camada de serviço (PersonService)
			final Person person = convert(dto);
      // TODO rename to save
			final Person personSaved = service.savePerson(person);
      return new ResponseEntity<Person>(personSaved, HttpStatus.CREATED);
      // TODO renomear exceção para PersonException
		} catch (final MyRuleException e) {
      // TODO o ideal aqui é lançar uma exceçao e tratar via exception mapper.
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("{id}")
	public ResponseEntity update( @PathVariable("id") final Integer id, @RequestBody final PersonDTO dto) {
    // TODO criar método update no serviço
		return service.getPersonByID(id).map(entity -> {
			try {
				final Person person = convert(dto);
				person.setId(entity.getId());
            // TODO rename to update
				final Person personSaved = service.updatePerson(person);
				System.out.println(personSaved);
				return ResponseEntity.ok(person);
			} catch (final Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet( () ->
        // TODO Usar exceção NotFoundException
			new ResponseEntity("Registro inexistente!", HttpStatus.BAD_REQUEST ));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity delete( @PathVariable("id") final Integer id) {
		return service.getPersonByID(id).map( entity -> {
          // TODO rename to delete
          // TODO delete pode ser apenas por ID, não precisa do objeto
			service.deletePerson(entity);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}).orElseGet( () -> 
			new ResponseEntity("Registro inexistente!", HttpStatus.BAD_REQUEST ));
	}
	
  // TODO migrar pra serviço
	private Person convert(final PersonDTO dto) {
    return new Person(null, dto.getName(), dto.getCpf());
	}
}
