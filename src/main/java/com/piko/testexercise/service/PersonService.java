package com.piko.testexercise.service;

import com.piko.testexercise.model.Address;
import com.piko.testexercise.model.Contact;
import com.piko.testexercise.model.Person;
import com.piko.testexercise.repository.AddressRepository;
import com.piko.testexercise.repository.ContactRepository;
import com.piko.testexercise.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final AddressRepository addressRepository;
    private final ContactRepository contactRepository;
    public PersonService(PersonRepository personRepository,
                         AddressRepository addressRepository,
                         ContactRepository contactRepository) {
        this.personRepository = personRepository;
        this.addressRepository = addressRepository;
        this.contactRepository = contactRepository;
    }

    public List<Person> getAllPersons(){
        return personRepository.findAll();
    }
    public Person getPersonById(UUID personId){
        return personRepository.findPersonByUuid(personId).orElseThrow();
    }

    public List<Address> getAddressForPerson(UUID personId){
        return addressRepository.findAllByPersonUuid(personId);
    }
    public List<Contact> getContactsForPerson(UUID personId){
        return contactRepository.findAllByPersonUuid(personId);
    }
    public Person createPerson(Person person){
        return personRepository.save(person);
    }
    public Person updatePerson(Person person){
        Optional<Person> optionalPerson = personRepository.findById(person.getUuid());
        if(optionalPerson.isEmpty()){
            //Person does not exist exception...
            throw new RuntimeException("Person does not exist!");
        }
        return personRepository.save(person);
    }
    public void deletePerson(UUID personId){
        Optional<Person> person = personRepository.findById(personId);
        if(person.isEmpty()){
            //Person does not exist exception...
            throw new RuntimeException("Person does not exist!");
        }
        personRepository.delete(person.get());
    }
}
