package com.piko.testexercise.service;

import com.piko.testexercise.model.Contact;
import com.piko.testexercise.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ContactService {
    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> getAllContacts(){
        return contactRepository.findAll();
    }
    public Contact getContactById(UUID id){
        return contactRepository.findById(id).orElseThrow();
    }
    public Contact createContact(Contact contact){
        return contactRepository.save(contact);
    }
    public Contact updateContact(Contact contact){
        Optional<Contact> contactOptional = contactRepository.findById(contact.getId());
        if(contactOptional.isEmpty()){
            throw new RuntimeException("Contact does not exist!");
        }
        return contactRepository.save(contact);
    }
    public void deleteContact(UUID id){
        Optional<Contact> contact = contactRepository.findById(id);
        if(contact.isEmpty()){
            throw new RuntimeException("Contact does not exist!");
        }
        contactRepository.delete(contact.get());
    }
}
