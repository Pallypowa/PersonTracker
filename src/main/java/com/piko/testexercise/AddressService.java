package com.piko.testexercise.service;

import com.piko.testexercise.dto.AddressDTO;
import com.piko.testexercise.exceptions.AddressLimitException;
import com.piko.testexercise.exceptions.RecordDoesNotExistException;
import com.piko.testexercise.model.Address;
import com.piko.testexercise.repository.AddressRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    public AddressService(AddressRepository addressRepository, ModelMapper modelMapper) {
        this.addressRepository = addressRepository;
        this.modelMapper = modelMapper;
    }

    public List<AddressDTO> getAllAddresses(){
        return addressRepository
                .findAll()
                .stream()
                .map(address -> modelMapper.map(address, AddressDTO.class))
                .sorted(Comparator.comparing(AddressDTO::getPersonUUid))
                .toList();
    }
    public AddressDTO getAddressById(UUID id){
        Address address = addressRepository.findById(id).orElseThrow();
        return modelMapper.map(address, AddressDTO.class);
    }
    public AddressDTO createAddress(AddressDTO addressDTO){
        //Check if the current user already has a temporary && a permanent address
        Address address = modelMapper.map(addressDTO, Address.class);
        UUID uuid = address.getPerson().getUuid();
        List<Address> addressesForUser = addressRepository.findAllByPersonUuid(uuid);
        if(!newAddressCanBeAdded(addressesForUser, address)){
            throw new AddressLimitException();
        }
        return modelMapper.map(addressRepository.save(address), AddressDTO.class);
    }
    public AddressDTO updateAddress(AddressDTO addressDTO){
        Address address = modelMapper.map(addressDTO, Address.class);
        Optional<Address> optionalAddress = addressRepository.findById(address.getUuid());
        if(optionalAddress.isEmpty()){
            throw new RecordDoesNotExistException();
        }

        UUID uuid = address.getPerson().getUuid();
        List<Address> addressesForUser = addressRepository.findAllByPersonUuid(uuid);
        if(addressCantBeUpdated(addressesForUser, address)){
            throw new AddressLimitException();
        }

        return modelMapper.map(addressRepository.save(address), AddressDTO.class);
    }

    public void deleteAddress(UUID id){
        Optional<Address> address = addressRepository.findById(id);
        if(address.isEmpty()){
            throw new RecordDoesNotExistException();
        }
        addressRepository.delete(address.get());
    }
    public List<AddressDTO> getAddressForPerson(UUID personId){
        return addressRepository
                .findAllByPersonUuid(personId)
                .stream().map( address -> modelMapper.map(address, AddressDTO.class))
                .toList();
    }

    private boolean newAddressCanBeAdded(List<Address> addresses, Address newAddress){
        // If the list is empty, the new address can always be added
        if(addresses.isEmpty()) return true;

        // If there's only one address, check if its type is different from the new address
        if(addresses.size() == 1) {
            Address address = addresses.get(0);
            return !(address.getAddressType().equals(newAddress.getAddressType()));
        }

        // If there are already two addresses, the new address cannot be added
        return false;
    }

    private boolean addressCantBeUpdated(List<Address> addressesForUser, Address address) {
        //If the person only has 1 address, he can update it freely
        if(addressesForUser.size() == 1) return false;
        //If the person already has 2 addresses, he can only modify the record if he does not change the AddressType
        Optional<Address> otherAddress = addressesForUser
                .stream()
                .filter( addr -> !addr.getUuid().equals(address.getUuid()))
                .findFirst();

        if(otherAddress.isEmpty()) throw new RecordDoesNotExistException();

        return otherAddress.get().getAddressType().equals(address.getAddressType());

    }
}
