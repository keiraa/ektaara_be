package com.ektaara.open_gem_gem.service;

import com.ektaara.open_gem_gem.entity.Address;
import com.ektaara.open_gem_gem.entity.User;
import com.ektaara.open_gem_gem.repository.AddressRepository;
import com.ektaara.open_gem_gem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public Address addAddress(Long userId, Address address) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        address.setUser(user);
        return addressRepository.save(address);
    }

    public List<Address> getAddressesByUserId(Long userId) {
        return addressRepository.findAllByUserId(userId);
    }

    public void deleteAddress(Long addressId) {
        addressRepository.deleteById(addressId);
    }
}

