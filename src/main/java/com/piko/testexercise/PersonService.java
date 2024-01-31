package com.piko.testexercise.service;

import com.piko.testexercise.dto.AddressDTO;
import com.piko.testexercise.dto.ContactDTO;
import com.piko.testexercise.dto.PersonDTO;
import com.piko.testexercise.exceptions.RecordDoesNotExistException;
import com.piko.testexercise.model.Person;
import com.piko.testexercise.repository.AddressRepository;
import com.piko.testexercise.repository.ContactRepository;
import com.piko.testexercise.repository.PersonRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final AddressService addressService;
    private final ContactService contactService;
    private final ModelMapper modelMapper; //To map DTO -> Model
    public PersonService(PersonRepository personRepository,
                         AddressRepository addressRepository,
                         ContactRepository contactRepository,
                         AddressService addressService, ContactService contactService, ModelMapper modelMapper) {
        this.personRepository = personRepository;
        this.addressService = addressService;
        this.contactService = contactService;
        this.modelMapper = modelMapper;
    }

    public List<PersonDTO> getAllPersons(){
        return personRepository
                .findAll()
                .stream()
                .map( person -> modelMapper.map(person, PersonDTO.class))
                .toList();
    }
    public PersonDTO getPersonById(UUID personId){
        Person person = personRepository.findById(personId).orElseThrow();
        return modelMapper.map(person, PersonDTO.class);
    }

    public List<AddressDTO> getAddressForPerson(UUID personId){
        return addressService.getAddressForPerson(personId);
    }
    public List<ContactDTO> getContactsForPerson(UUID personId){
        return contactService.getContactsForPerson(personId);
    }
    public PersonDTO createPerson(PersonDTO personDTO){
        Person person = modelMapper.map(personDTO, Person.class);
        return modelMapper.map(personRepository.save(person), PersonDTO.class);
    }
    public PersonDTO updatePerson(PersonDTO personDTO){
        if(personDTO.getUuid() == null) throw new RecordDoesNotExistException();

        Person person = modelMapper.map(personDTO, Person.class);

        Optional<Person> optionalPerson = personRepository.findById(person.getUuid());
        if(optionalPerson.isEmpty()){
            throw new RecordDoesNotExistException();
        }

        return modelMapper.map(personRepository.save(person), PersonDTO.class);
    }
    public void deletePerson(UUID personId){
        Optional<Person> person = personRepository.findById(personId);
        if(person.isEmpty()){
            throw new RecordDoesNotExistException();
        }
        personRepository.delete(person.get());
    }
}
