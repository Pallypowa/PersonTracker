package com.piko.testexercise.service;

import com.piko.testexercise.dto.ContactDTO;
import com.piko.testexercise.model.Contact;
import com.piko.testexercise.model.ContactType;
import com.piko.testexercise.model.Person;
import com.piko.testexercise.repository.ContactRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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
import java.util.UUID;


@ExtendWith(MockitoExtension.class)
class ContactServiceTest {

    @Mock
    private ContactRepository contactRepository;
    @Spy
    private ModelMapper modelMapper;
    @InjectMocks
    ContactService contactService;

    @Test
    public void shouldReturnAllContacts(){
        //Arrange
        setupTestData();
        //Act
        List<ContactDTO> returnedContacts = contactService.getAllContacts();
        //Assert
        Assertions.assertNotNull(returnedContacts);
    }

    private void setupTestData(){
        //Add 2 mocked Contact
        List<Contact> contacts = new ArrayList<>();
        contacts.add(Contact
                .builder()
                .contactType(ContactType.EMAIL)
                .contact("e-mail")
                .person(Person
                        .builder()
                        .uuid(UUID.randomUUID())
                        .build())
                .build());

        contacts.add(Contact
                .builder()
                .contactType(ContactType.PHONE)
                .contact("PHONE")
                .person(Person
                        .builder()
                        .uuid(UUID.randomUUID())
                        .build())
                .build());

        Mockito.when(contactRepository.findAll()).thenReturn(contacts);
    }
}