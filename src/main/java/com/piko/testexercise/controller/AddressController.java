package com.piko.testexercise.controller;

import com.piko.testexercise.model.Address;
import com.piko.testexercise.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/api/addresses")
    public ResponseEntity<?> getAllAddresses(){
        List<Address> addresses = addressService.getAllAddresses();
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }
    @GetMapping("/api/addresses/{id}")
    public ResponseEntity<?> getAddress(@NonNull @PathVariable UUID id){
        Address address = addressService.getAddressById(id);
        return new ResponseEntity<>(address, HttpStatus.OK);
    }
    @PostMapping("/api/addresses")
    public ResponseEntity<?> createAddress(@NonNull @RequestBody Address address){
        Address createdAddress = addressService.createAddress(address);
        return new ResponseEntity<>(createdAddress, HttpStatus.CREATED);
    }
    @PutMapping("/api/addresses")
    public ResponseEntity<?> updateAddress(@NonNull @RequestBody Address address){
        Address updatedAddress = addressService.updateAddress(address);
        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
    }
    @DeleteMapping("/api/addresses/{id}")
    public ResponseEntity<?> deleteAddress(@NonNull @PathVariable UUID id){
        addressService.deleteAddress(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
