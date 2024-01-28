package com.piko.testexercise.controller;

import com.piko.testexercise.model.Contact;
import com.piko.testexercise.service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ContactController {
    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/api/contacts")
    public ResponseEntity<?> getAllContacts(){
        List<Contact> contacts = contactService.getAllContacts();
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }
    @GetMapping("/api/contacts/{id}")
    public ResponseEntity<?> getContact(@NonNull @PathVariable UUID id){
        Contact contact = contactService.getContactById(id);
        return new ResponseEntity<>(contact, HttpStatus.OK);
    }
    @PostMapping("/api/contacts")
    public ResponseEntity<?> createContact(@NonNull @RequestBody Contact contact){
        Contact createdContact = contactService.createContact(contact);
        return new ResponseEntity<>(createdContact, HttpStatus.OK);
    }
    @PutMapping("/api/contacts")
    public ResponseEntity<?> updateContact(@NonNull @RequestBody Contact contact){
        Contact updatedContact = contactService.updateContact(contact);
        return new ResponseEntity<>(updatedContact, HttpStatus.OK);
    }
    @DeleteMapping("/api/contacts/{id}")
    public ResponseEntity<?> deleteContact(@NonNull  @PathVariable UUID id){
        contactService.deleteContact(id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }
}
