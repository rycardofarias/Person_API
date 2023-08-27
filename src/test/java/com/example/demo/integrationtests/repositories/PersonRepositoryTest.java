package com.example.demo.integrationtests.repositories;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.integrationtests.testcontainers.AbstractIntegrationTest;
import com.example.demo.model.Person;
import com.example.demo.repositories.PersonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
public class PersonRepositoryTest extends AbstractIntegrationTest{

	@Autowired
	public PersonRepository repository;
	
	private static Person person;
	
	@BeforeAll
	public static void setup() {
		person = new Person();
	}
	
	@Test
	@Order(1)
	public void testFindByName() throws JsonMappingException, JsonProcessingException {
		
		Pageable pageable = PageRequest.of(3, 13, Sort.by(Direction.ASC, "firstName"));
		person = repository.findPersonByName("ar", pageable).getContent().get(0);
		
		assertNotNull(person.getId());
		assertNotNull(person.getFirstName());
		assertNotNull(person.getLastName());
		assertNotNull(person.getAddress());
		assertNotNull(person.getGender());
		assertTrue(person.getEnabled());

		assertEquals(541, person.getId());
		
		assertEquals("Clarence", person.getFirstName());
		assertEquals("Kynsey", person.getLastName());
		assertEquals("216 Jay Terrace", person.getAddress());
		assertEquals("Male", person.getGender());
	}
	
	@Test
	@Order(2)
	public void testDiablePerson() throws JsonMappingException, JsonProcessingException {
		
		repository.disablePerson(person.getId());
		
		Pageable pageable = PageRequest.of(3, 13, Sort.by(Direction.ASC, "firstName"));
		person = repository.findPersonByName("ar", pageable).getContent().get(0);
		
		assertNotNull(person.getId());
		assertNotNull(person.getFirstName());
		assertNotNull(person.getLastName());
		assertNotNull(person.getAddress());
		assertNotNull(person.getGender());

		assertFalse(person.getEnabled());

		assertEquals(963, person.getId());
		
		assertEquals("Clarence", person.getFirstName());
		assertEquals("Avrasin", person.getLastName());
		assertEquals("328 Hintze Point", person.getAddress());
		assertEquals("Male", person.getGender());
	}
}
