package com.piko.testexercise.controller;

import com.piko.testexercise.model.Address;
import com.piko.testexercise.model.Contact;
import com.piko.testexercise.model.Person;
import com.piko.testexercise.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/api/persons")
    public ResponseEntity<?> getAllPersons(){
        List<Person> persons = personService.getAllPersons();
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }
    @GetMapping("/api/persons/{id}")
    public ResponseEntity<?> getPersonById(@NonNull  @PathVariable UUID id){
        Person person = personService.getPersonById(id);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }
    @GetMapping("/api/persons/{id}/addresses")
    public ResponseEntity<?> getPersonAddress(@NonNull @PathVariable UUID id){
        List<Address> addresses = personService.getAddressForPerson(id);
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }
    @GetMapping("/api/persons/{id}/contacts")
    public ResponseEntity<?> getPersonContacts(@NonNull @PathVariable UUID id){
        List<Contact> contacts = personService.getContactsForPerson(id);
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    @PostMapping("/api/persons")
    public ResponseEntity<?> createPerson(@NonNull @RequestBody Person person){
        Person createdPerson = personService.createPerson(person);
        return new ResponseEntity<>(createdPerson, HttpStatus.CREATED);
    }

    @PutMapping ("/api/persons")
    public ResponseEntity<?> updatePerson(@NonNull @RequestBody Person person){
        Person updatedPerson = personService.updatePerson(person);
        return new ResponseEntity<>(updatedPerson, HttpStatus.OK);
    }

    @DeleteMapping ("/api/persons/{id}")
    public ResponseEntity<?> deletePerson(@NonNull @PathVariable UUID id){
        personService.deletePerson(id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }
}
