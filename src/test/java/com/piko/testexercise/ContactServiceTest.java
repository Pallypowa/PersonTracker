package com.piko.testexercise.service;

import com.piko.testexercise.dto.ContactDTO;
import com.piko.testexercise.exceptions.RecordDoesNotExistException;
import com.piko.testexercise.model.Contact;
import com.piko.testexercise.model.ContactType;
import com.piko.testexercise.model.Person;
import com.piko.testexercise.repository.ContactRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ContactServiceTest {

    @InjectMocks
    private ContactService contactService;
    @Mock
    private ContactRepository contactRepository;
    @Spy
    private ModelMapper modelMapper;

    @Test
    public void shouldReturnAllContacts() {
        //Arrange
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

        Mockito.when(contactRepository.findAll()).thenReturn(contacts);

        //Act
        List<ContactDTO> returnedContacts = contactService.getAllContacts();
        //Assert
        assertNotNull(returnedContacts);
    }

    @Test
    public void shouldReturnContactById() {
        UUID id = UUID.randomUUID();
        Contact contact = Contact
                .builder()
                .contactType(ContactType.EMAIL)
                .contact("e-mail")
                .person(Person
                        .builder()
                        .uuid(UUID.randomUUID())
                        .build())
                .uuid(id)
                .build();

        Mockito.when(contactRepository.findById(id)).thenReturn(Optional.of(contact));

        ContactDTO contactDTO = contactService.getContactById(id);
        assertNotNull(contactDTO);
    }

    @Test
    public void shouldCreateContact(){
        ContactDTO inputDTO = new ContactDTO(null, ContactType.EMAIL, "email", UUID.randomUUID());
        Contact contact = Contact.builder().uuid(UUID.randomUUID()).contactType(ContactType.EMAIL).contact("e-mail").build();

        Mockito.when(contactRepository.save(Mockito.any(Contact.class)))
                .thenReturn(contact);

        ContactDTO contactDTO = contactService.createContact(inputDTO);

        assertNotNull(contactDTO);
    }

    @Test
    public void shouldUpdateContact(){
        UUID id = UUID.randomUUID();
        ContactDTO inputDTO = new ContactDTO(id, ContactType.EMAIL, "email", UUID.randomUUID());
        Contact contact = Contact.builder().uuid(id).contactType(ContactType.EMAIL).contact("e-mail").build();

        Mockito.when(contactRepository.findById(id)).thenReturn(Optional.of(contact));
        Mockito.when(contactRepository.save(Mockito.any(Contact.class)))
                .thenReturn(contact);

        ContactDTO contactDTO = contactService.updateContact(inputDTO);

        assertNotNull(contactDTO);
    }

    @Test
    public void shouldFailUpdateContact(){
        //Should fail because there is no contact with the given id
        ContactDTO inputDTO = new ContactDTO(UUID.randomUUID(), ContactType.EMAIL, "email", UUID.randomUUID());

        assertThrows(RecordDoesNotExistException.class, () -> contactService.updateContact(inputDTO));
    }

    @Test
    public void shouldDeleteContact(){
        UUID id = UUID.randomUUID();
        Contact contact = Contact.builder().uuid(id).contactType(ContactType.EMAIL).contact("e-mail").build();

        Mockito.when(contactRepository.findById(id)).thenReturn(Optional.of(contact));

        assertDoesNotThrow(() -> contactService.deleteContact(id));
    }

    @Test
    public void shouldFailDeleteContact(){
        assertThrows(RecordDoesNotExistException.class, () -> contactService.deleteContact(UUID.randomUUID()));
    }

    @Test
    public void shouldReturnContactsByPersonId(){
        UUID personId = UUID.randomUUID();
        Person person = Person.builder().uuid(personId).name("person").build();
        List<Contact> contacts = new ArrayList<>();

        contacts.add(Contact.builder().person(person).contactType(ContactType.EMAIL).contact("email").build());
        contacts.add(Contact.builder().person(person).contactType(ContactType.PHONE).contact("phone").build());

        Mockito.when(contactRepository.findAllByPersonUuid(personId)).thenReturn(contacts);

        List<ContactDTO> contactsForPerson = contactService.getContactsForPerson(personId);
        assertNotNull(contactsForPerson);
    }
}