package com.ektaara.open_gem_gem.service;

import com.ektaara.open_gem_gem.entity.Address;

import java.util.List;

public interface AddressService {

    Address addAddress(Long userId, Address address);

    List<Address> getAddressesByUserId(Long userId);

    void deleteAddress(Long addressId);
}
