package com.ribeiro.restApiCpf.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;

@Entity
@Table(name="person")
@Builder
@Data
public class Person {
	
	@Id
	@Column(name="id_person")
	private Integer id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="cpf")
	private String cpf;

}
