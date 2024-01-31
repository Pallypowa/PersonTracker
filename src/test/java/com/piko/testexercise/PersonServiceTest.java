package com.piko.testexercise.service;

import com.piko.testexercise.dto.PersonDTO;
import com.piko.testexercise.exceptions.RecordDoesNotExistException;
import com.piko.testexercise.model.Person;
import com.piko.testexercise.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
    @Mock
    private PersonRepository personRepository;
    @Spy
    private ModelMapper modelMapper;
    @InjectMocks
    private PersonService personService;

    @Test
    public void shouldReturnAllPersons(){
        Person person = Person.builder().uuid(UUID.randomUUID()).name("person").build();
        List<Person> persons = new ArrayList<>();
        persons.add(person);

        Mockito.when(personRepository.findAll()).thenReturn(persons);

        List<PersonDTO> allPersons = personService.getAllPersons();
        assertNotNull(allPersons);
    }

    @Test
    public void shouldReturnPersonById(){
        UUID personId = UUID.randomUUID();
        Person person = Person.builder().uuid(personId).name("person").build();

        Mockito.when(personRepository.findById(personId)).thenReturn(Optional.of(person));

        PersonDTO personById = personService.getPersonById(personId);
        assertNotNull(personById);
    }

    @Test
    public void shouldCreatePerson(){
        PersonDTO personDTO = new PersonDTO(null, "person", null);
        Person person = Person.builder().uuid(UUID.randomUUID()).name("person").build();

        Mockito.when(personRepository.save(Mockito.any(Person.class))).thenReturn(person);

        PersonDTO createdPerson = personService.createPerson(personDTO);

        assertNotNull(createdPerson);
    }

    @Test
    public void shouldUpdatePerson(){
        UUID personId = UUID.randomUUID();
        PersonDTO personDTO = new PersonDTO(personId, "person", null);
        Person person = Person.builder().uuid(personId).name("person").build();

        Mockito.when(personRepository.findById(personId)).thenReturn(Optional.of(person));
        Mockito.when(personRepository.save(Mockito.any(Person.class))).thenReturn(person);

        PersonDTO updatePerson = personService.updatePerson(personDTO);

        assertNotNull(updatePerson);
    }

    @Test
    public void shouldFailUpdatePerson(){
        PersonDTO personDTO = new PersonDTO(UUID.randomUUID(), "person", null);

        assertThrows(RecordDoesNotExistException.class, () -> personService.updatePerson(personDTO));
    }

    @Test
    public void shouldDeletePerson(){
        UUID personId = UUID.randomUUID();
        Person person = Person.builder().uuid(personId).name("person").build();

        Mockito.when(personRepository.findById(personId)).thenReturn(Optional.ofNullable(person));

        assertDoesNotThrow(() -> personService.deletePerson(personId));
    }

    @Test
    public void shouldFailDeletePerson(){
        assertThrows(RecordDoesNotExistException.class, () -> personService.deletePerson(UUID.randomUUID()));
    }
}
