package com.ribeiro.restApiCpf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ribeiro.restApiCpf.model.entity.Person;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonKafkaService {
	
	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	
	public void send(Person person) {
		String personJson;
		try {
			personJson = new ObjectMapper().writeValueAsString(person);
			kafkaTemplate.send("votacao-concluida",personJson);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

}
