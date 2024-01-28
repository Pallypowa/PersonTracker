package com.piko.testexercise.repository;

import com.piko.testexercise.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ContactRepository extends JpaRepository<Contact, UUID> {
    List<Contact> findAllByPersonUuid(UUID id);
}
