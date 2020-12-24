package com.ribeiro.restApiCpf.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="person")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Person {

  public Person(final String name, final String cpf) {
    super();
    this.name = name;
    this.cpf = cpf;
  }

  @Id
	@Column(name="id_person")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="cpf")
	private String cpf;

}
