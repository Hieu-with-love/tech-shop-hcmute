package com.hcmute.tech_shop.services.Impl;

import com.hcmute.tech_shop.entities.Address;
import com.hcmute.tech_shop.repositories.AddressRepository;
import com.hcmute.tech_shop.services.interfaces.IAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements IAddressService {
    private final AddressRepository addressRepository;

    @Override
    public List<Address> findByUser_Username(String userUsername) {
        return addressRepository.findByUser_Username(userUsername);
    }

    @Override
    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    @Override
    public <S extends Address> S save(S entity) {
        return addressRepository.save(entity);
    }

    @Override
    public Optional<Address> findById(Long aLong) {
        return addressRepository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return addressRepository.existsById(aLong);
    }

    @Override
    public long count() {
        return addressRepository.count();
    }

    @Override
    public void deleteById(Long aLong) {
        addressRepository.deleteById(aLong);
    }

    @Override
    public void deleteAll() {
        addressRepository.deleteAll();
    }

    @Override
    public void updateAddress(Map<String, String> params) {
        Address currentAddress = this.findById(Long.parseLong(params.get("addressId"))).get();
        currentAddress.setCity(params.get("city"));
        currentAddress.setDistrict(params.get("district"));
        currentAddress.setStreet(params.get("street"));
        currentAddress.setDetailLocation(params.get("detailLocation"));
        addressRepository.save(currentAddress);
    }
}
