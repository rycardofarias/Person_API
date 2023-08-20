package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.data.vo.v1.PersonVO;
import com.example.demo.services.PersonServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/person/v1")
@Tag(name = "People", description = "Endpoint for Managing People")
public class PersonController {
	
	@Autowired
	private PersonServices service;
	
	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, 
			MediaType.APPLICATION_XML_VALUE})
	@Operation(summary = "Finds all People", 
		description = "Finds all People",
		tags = {"People"}, 
		responses = {
			@ApiResponse(description = "Sucess", responseCode = "200", 
					content = {
							@Content(mediaType = "application/json", 
									array = @ArraySchema(
											schema = @Schema(
													implementation = PersonVO.class)))}),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			}
	)
	public ResponseEntity<PagedModel<EntityModel<PersonVO>>> findAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "12") Integer size,
			@RequestParam(value = "limit", defaultValue = "12") String direction
			) {
		
		var sortDirection = "desc".contentEquals(direction)
				? Direction.DESC : Direction.ASC;
		
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));
		return ResponseEntity.ok(service.findAll(pageable));

	}
	
	
	@GetMapping(value = "/{id}", 
			produces = {MediaType.APPLICATION_JSON_VALUE, 
					MediaType.APPLICATION_XML_VALUE})
	@Operation(summary = "Finds a People", 
		description = "Finds a People",
		tags = {"People"}, 
		responses = {
			@ApiResponse(description = "Sucess", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = PersonVO.class))
			),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			}
	)
	public PersonVO findById( @PathVariable(value = "id") Long id){
		return service.findById(id);
	}
	
	
	
	@PostMapping(
			consumes = {MediaType.APPLICATION_JSON_VALUE, 
					MediaType.APPLICATION_XML_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, 
					MediaType.APPLICATION_XML_VALUE})
	@Operation(summary = "Adds a new People", 
		description = "Adds a new People by passing in a JSON or XML representation of the person",
		tags = {"People"}, 
		responses = {
			@ApiResponse(description = "Sucess", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = PersonVO.class))
			),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			}
	)
	public PersonVO create( @RequestBody PersonVO person){
		return service.create(person);
	}
	
	
	
	@PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, 
			MediaType.APPLICATION_XML_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, 
					MediaType.APPLICATION_XML_VALUE})
	@Operation(summary = "Adds a new People", 
		description = "Updates a person by passing in a JSON or XML representation of the person",
		tags = {"People"}, 
		responses = {
			@ApiResponse(description = "Updated", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = PersonVO.class))
			),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			}
	)
	public PersonVO update( @RequestBody PersonVO person){
		return service.update(person);
	}
	
	@DeleteMapping(value = "/{id}",   
			produces = {MediaType.APPLICATION_JSON_VALUE, 
					MediaType.APPLICATION_XML_VALUE})
	@Operation(summary = "Adds a new People", 
	description = "Deletes a person by passing in a JSON or XML representation of the person",
	tags = {"People"}, 
	responses = {
		@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
		@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
		@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
		}
	)
	public ResponseEntity<?> delete( @PathVariable(value = "id") Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PatchMapping(value = "/{id}", 
			produces = {MediaType.APPLICATION_JSON_VALUE, 
					MediaType.APPLICATION_XML_VALUE})
	@Operation(summary = "Disable a specific Person by your ID", 
		description = "Disable a specific Person by your ID",
		tags = {"People"}, 
		responses = {
			@ApiResponse(description = "Sucess", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = PersonVO.class))
			),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			}
	)
	public PersonVO disablePerson( @PathVariable(value = "id") Long id){
		return service.disablePerson(id);
	}
}
