package com.example.demo.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.controllers.PersonController;
import com.example.demo.data.vo.v1.PersonVO;
import com.example.demo.exceptions.RequiredObjectsIsNullException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.mapper.DozerMapper;
import com.example.demo.model.Person;
import com.example.demo.repositories.PersonRepository;

import jakarta.transaction.Transactional;

@Service
public class PersonServices {

	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	@Autowired
	PersonRepository repository;
	
	public Page<PersonVO> findAll(Pageable pageable) {
		
		logger.info("Finding all persons!");
		
		var personPage = repository.findAll(pageable);
		
		var personVOsPage =  personPage.map(p -> DozerMapper.parseObject(p, PersonVO.class));
		
		personVOsPage.map(p -> p.add(
				linkTo(methodOn(
						PersonController.class).findById(p.getKey())).withSelfRel()));
		
		return personVOsPage;
	}
	
	public PersonVO findById(Long id) {
			
			logger.info("Finding one person!");
			
			var entity =  repository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException(
							"No records found for this ID!"));
			var vo = DozerMapper.parseObject(entity, PersonVO.class);
			vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
			return vo;
	}

	public PersonVO create(PersonVO person) {
		
		if(person == null) throw new RequiredObjectsIsNullException();
		
		logger.info("Creating one person!");
		var entity = DozerMapper.parseObject(person, Person.class);
		var vo = DozerMapper.parseObject(repository.save(entity),PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;

	}
	
	public PersonVO update(PersonVO person) {
		
		if(person == null) throw new RequiredObjectsIsNullException();
		
		logger.info("Update one person!");
		
		var entity = repository.findById(person.getKey())
		.orElseThrow(() -> new ResourceNotFoundException(
				"No records found for this ID!"));
		
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		var vo = DozerMapper.parseObject(repository.save(entity),PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;

	}
	
	public void delete(Long id) {
		logger.info("Deleting one person!");
		
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(
						"No records found for this ID!"));
		repository.delete(entity);
		
	}

	@Transactional
	public PersonVO disablePerson(Long id) {
		
		logger.info("Disabling one person!");
		
		repository.disablePerson(id);
				
		var entity = repository.findById(id)
		.orElseThrow(() -> new ResourceNotFoundException(
				"No records found for this ID!"));
		
		var vo = DozerMapper.parseObject(repository.save(entity),PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;

	}
}
