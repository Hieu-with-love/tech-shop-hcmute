package com.hcmute.tech_shop.services.Impl;

import com.hcmute.tech_shop.entities.Brand;
import com.hcmute.tech_shop.repositories.BrandRepository;
import com.hcmute.tech_shop.services.interfaces.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceImpl implements IBrandService {
    @Autowired
    BrandRepository brandRepository;

    @Override
    public Brand findByName(String name) {
        return brandRepository.findByName(name);
    }

    @Override
    public <S extends Brand> List<S> findAll(Example<S> example) {
        return brandRepository.findAll(example);
    }

    @Override
    public <S extends Brand> List<S> findAll(Example<S> example, Sort sort) {
        return brandRepository.findAll(example, sort);
    }

    @Override
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    @Override
    public <S extends Brand> S save(S entity) {
        return brandRepository.save(entity);
    }

    @Override
    public Optional<Brand> findById(Long aLong) {
        return brandRepository.findById(aLong);
    }

    @Override
    public long count() {
        return brandRepository.count();
    }

    @Override
    public boolean existsById(Long aLong) {
        return brandRepository.existsById(aLong);
    }

    @Override
    public void deleteById(Long aLong) {
        brandRepository.deleteById(aLong);
    }

    @Override
    public void delete(Brand entity) {
        brandRepository.delete(entity);
    }

    @Override
    public void deleteAll() {
        brandRepository.deleteAll();
    }

    @Override
    public List<Brand> findAll(Sort sort) {
        return brandRepository.findAll(sort);
    }
}
