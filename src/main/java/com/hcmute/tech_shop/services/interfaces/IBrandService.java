package com.hcmute.tech_shop.services.interfaces;

import com.hcmute.tech_shop.dtos.requests.BrandRequest;
import com.hcmute.tech_shop.entities.Brand;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IBrandService {

    Brand findByName(String name);

    <S extends Brand> List<S> findAll(Example<S> example);

    <S extends Brand> List<S> findAll(Example<S> example, Sort sort);

    void init();

    List<Brand> findAll();

    <S extends Brand> S save(S entity);

    Optional<Brand> findById(Long aLong);

    long count();

    boolean existsById(Long aLong);

    void deleteById(Long aLong);



    List<Brand> findAll(Sort sort);

    boolean deleteImage(String filename);

    boolean addBrand(BrandRequest brandRequest, MultipartFile file) throws IOException;

    boolean updateBrand(BrandRequest brandRequest, Long id, String oldImg);

    boolean updateBrand(BrandRequest brandRequest, Long id, String oldImg, MultipartFile file);

    boolean deleteBrand(Long brandId);
}
