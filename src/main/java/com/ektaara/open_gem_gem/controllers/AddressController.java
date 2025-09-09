package com.ektaara.open_gem_gem.controllers;

import com.ektaara.open_gem_gem.entity.Address;
import com.ektaara.open_gem_gem.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<Address> addAddress(@PathVariable Long userId, @RequestBody Address address) {
        return ResponseEntity.ok(addressService.addAddress(userId, address));
    }

    @GetMapping
    public ResponseEntity<List<Address>> getAll(@PathVariable Long userId) {
        return ResponseEntity.ok(addressService.getAddressesByUserId(userId));
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<?> delete(@PathVariable Long addressId) {
        addressService.deleteAddress(addressId);
        return ResponseEntity.ok("Deleted");
    }
}
