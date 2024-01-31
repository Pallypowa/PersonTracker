package com.piko.testexercise.service;

import com.piko.testexercise.dto.ContactDTO;
import com.piko.testexercise.exceptions.RecordDoesNotExistException;
import com.piko.testexercise.model.Contact;
import com.piko.testexercise.repository.ContactRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ContactService {
    private final ContactRepository contactRepository;
    private final ModelMapper modelMapper;
    public ContactService(ContactRepository contactRepository, ModelMapper modelMapper) {
        this.contactRepository = contactRepository;
        this.modelMapper = modelMapper;
    }

    public List<ContactDTO> getAllContacts(){
        return contactRepository
                .findAll()
                .stream()
                .map( contact -> modelMapper.map(contact, ContactDTO.class))
                .sorted(Comparator.comparing(ContactDTO::getPersonUUid))
                .toList();
    }
    public ContactDTO getContactById(UUID id){
        Contact contact = contactRepository.findById(id).orElseThrow();
        return modelMapper.map(contact, ContactDTO.class);
    }
    public ContactDTO createContact(ContactDTO contactDTO){
        Contact contact = modelMapper.map(contactDTO, Contact.class);
        return modelMapper.map(contactRepository.save(contact), ContactDTO.class);
    }
    public ContactDTO updateContact(ContactDTO contactDTO){
        Contact contact = modelMapper.map(contactDTO, Contact.class);
        Optional<Contact> contactOptional = contactRepository.findById(contact.getUuid());
        if(contactOptional.isEmpty()){
            throw new RecordDoesNotExistException();
        }
        return modelMapper.map(contactRepository.save(contact), ContactDTO.class);
    }
    public void deleteContact(UUID id){
        Optional<Contact> contact = contactRepository.findById(id);
        if(contact.isEmpty()){
            throw new RecordDoesNotExistException();
        }
        contactRepository.delete(contact.get());
    }
    public List<ContactDTO> getContactsForPerson(UUID personId){
        return contactRepository
                .findAllByPersonUuid(personId)
                .stream()
                .map( contact -> modelMapper.map(contact, ContactDTO.class))
                .toList();
    }
}
