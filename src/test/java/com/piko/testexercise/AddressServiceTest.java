package com.piko.testexercise.service;

import com.piko.testexercise.dto.AddressDTO;
import com.piko.testexercise.exceptions.AddressLimitException;
import com.piko.testexercise.model.Address;
import com.piko.testexercise.model.AddressType;
import com.piko.testexercise.model.Person;
import com.piko.testexercise.repository.AddressRepository;
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
public class AddressServiceTest {
    @Mock
    private AddressRepository addressRepository;
    @Spy
    private ModelMapper modelMapper;
    @InjectMocks
    private AddressService addressService;

    @Test
    public void shouldReturnAllAddresses(){
        Address address = Address.builder().uuid(UUID.randomUUID()).city("Budapest").build();
        List<Address> addresses = new ArrayList<>();
        addresses.add(address);

        Mockito.when(addressRepository.findAll()).thenReturn(addresses);

        List<AddressDTO> allAddresses = addressService.getAllAddresses();

        assertNotNull(allAddresses);
    }

    @Test
    public void shouldReturnAddressById(){
        UUID addressId = UUID.randomUUID();
        Address address = Address.builder().uuid(addressId).city("Budapest").build();

        Mockito.when(addressRepository.findById(addressId)).thenReturn(Optional.ofNullable(address));

        AddressDTO addressById = addressService.getAddressById(addressId);

        assertNotNull(addressById);
    }

    @Test
    public void shouldReturnAddressByPersonId(){
        UUID personId = UUID.randomUUID();

        Address address = Address.builder().person(Person.builder().uuid(personId).build()).city("Budapest").build();

        Mockito.when(addressRepository.findAllByPersonUuid(personId)).thenReturn(List.of(address));

        List<AddressDTO> addressForPerson = addressService.getAddressForPerson(personId);

        assertNotNull(addressForPerson);
    }

    @Test
    public void shouldCreateAddress(){
        AddressDTO addressDTO = AddressDTO.builder().personUUid(UUID.randomUUID()).city("Budapest").build();
        Address address = Address.builder().uuid(UUID.randomUUID()).streetName("Budapest").build();

        Mockito.when(addressRepository.findAllByPersonUuid(Mockito.any(UUID.class))).thenReturn(new ArrayList<>());
        Mockito.when(addressRepository.save(Mockito.any(Address.class))).thenReturn(address);

        AddressDTO createdAddress = addressService.createAddress(addressDTO);

        assertNotNull(createdAddress);
    }

    @Test
    public void shouldFailCreateAddressWithSameAddressType(){
        AddressDTO addressDTO = AddressDTO.builder().personUUid(UUID.randomUUID()).city("Budapest").addressType(AddressType.PERMANENT).build();
        Address address = Address.builder().uuid(UUID.randomUUID()).streetName("Budapest").addressType(AddressType.PERMANENT).build();

        Mockito.when(addressRepository.findAllByPersonUuid(Mockito.any(UUID.class))).thenReturn(List.of(address));

        assertThrows(AddressLimitException.class, () -> addressService.createAddress(addressDTO));
    }

    @Test
    public void shouldFailCreateAddressWithTwoAddresses(){
        AddressDTO addressDTO = AddressDTO.builder().personUUid(UUID.randomUUID()).city("Budapest").addressType(AddressType.PERMANENT).build();
        List<Address> addresses = new ArrayList<>();

        addresses.add(Address.builder().uuid(UUID.randomUUID()).streetName("Budapest").addressType(AddressType.PERMANENT).build());
        addresses.add(Address.builder().uuid(UUID.randomUUID()).streetName("Veszprem").addressType(AddressType.TEMPORARY).build());
        Mockito.when(addressRepository.findAllByPersonUuid(Mockito.any(UUID.class))).thenReturn(addresses);

        assertThrows(AddressLimitException.class, () -> addressService.createAddress(addressDTO));
    }

    @Test
    public void shouldUpdateAddress(){
        UUID addressId = UUID.randomUUID();
        AddressDTO addressDTO = AddressDTO.builder().uuid(addressId).personUUid(UUID.randomUUID()).city("Budapest").build();
        Address address = Address.builder().uuid(addressId).streetName("Erd").build();

        Mockito.when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));
        Mockito.when(addressRepository.findAllByPersonUuid(Mockito.any(UUID.class))).thenReturn(List.of(address));
        Mockito.when(addressRepository.save(Mockito.any(Address.class))).thenReturn(address);

        AddressDTO updatedAddress = addressService.updateAddress(addressDTO);

        assertNotNull(updatedAddress);
    }
    @Test
    public void shouldFailUpdateAddress(){
        UUID addressId = UUID.randomUUID();
        List<Address> addresses = new ArrayList<>();
        AddressDTO addressDTO = AddressDTO
                .builder()
                .uuid(addressId)
                .personUUid(UUID.randomUUID())
                .city("Budapest")
                .addressType(AddressType.TEMPORARY)
                .build();

        Address addressToUpdate = Address
                .builder()
                .uuid(addressId)
                .streetName("Erd")
                .addressType(AddressType.PERMANENT)
                .build();

        addresses.add(addressToUpdate);
        addresses.add(Address.builder().uuid(UUID.randomUUID()).streetName("Szentendre").addressType(AddressType.TEMPORARY).build());

        Mockito.when(addressRepository.findById(addressId)).thenReturn(Optional.of(addressToUpdate));
        Mockito.when(addressRepository.findAllByPersonUuid(Mockito.any(UUID.class))).thenReturn(addresses);


        assertThrows(AddressLimitException.class, () -> addressService.updateAddress(addressDTO));
    }

    @Test
    public void shouldDeleteAddress(){
        UUID addressId = UUID.randomUUID();
        Address address = Address.builder().uuid(addressId).city("Budapest").build();

        Mockito.when(addressRepository.findById(addressId)).thenReturn(Optional.ofNullable(address));

        assertDoesNotThrow(() -> addressService.deleteAddress(addressId));
    }
}
