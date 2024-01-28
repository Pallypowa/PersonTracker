package com.piko.testexercise.service;

import com.piko.testexercise.exceptions.AddressLimitException;
import com.piko.testexercise.exceptions.RecordDoesNotExistException;
import com.piko.testexercise.model.Address;
import com.piko.testexercise.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<Address> getAllAddresses(){
        return addressRepository.findAll();
    }
    public Address getAddressById(UUID id){
        return addressRepository.findById(id).orElseThrow();
    }
    public Address createAddress(Address address){
        //Check if the current user already has a temporary && a permanent address
        UUID uuid = address.getPerson().getUuid();
        List<Address> addressesForUser = addressRepository.findAllByPersonUuid(uuid);
        if(!newAddressCanBeAdded(addressesForUser, address)){
            throw new AddressLimitException();
        }
        return addressRepository.save(address);
    }
    public Address updateAddress(Address address){
        Optional<Address> optionalAddress = addressRepository.findById(address.getUuid());
        if(optionalAddress.isEmpty()){
            throw new RecordDoesNotExistException();
        }

        UUID uuid = address.getPerson().getUuid();
        List<Address> addressesForUser = addressRepository.findAllByPersonUuid(uuid);
        if(!newAddressCanBeAdded(addressesForUser, address)){
            throw new AddressLimitException();
        }

        return addressRepository.save(address);
    }
    public void deleteAddress(UUID id){
        Optional<Address> address = addressRepository.findById(id);
        if(address.isEmpty()){
            throw new AddressLimitException();
        }
        addressRepository.delete(address.get());
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
}
