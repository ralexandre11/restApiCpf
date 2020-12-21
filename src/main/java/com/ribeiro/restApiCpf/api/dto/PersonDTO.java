package com.ribeiro.restApiCpf.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PersonDTO {

	private String name;
	private String Cpf;
}
