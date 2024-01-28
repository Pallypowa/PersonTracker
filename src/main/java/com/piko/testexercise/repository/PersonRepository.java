package com.piko.testexercise.repository;

import com.piko.testexercise.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, UUID> {
    Optional<Person> findPersonByUuid(UUID uuid);
}
