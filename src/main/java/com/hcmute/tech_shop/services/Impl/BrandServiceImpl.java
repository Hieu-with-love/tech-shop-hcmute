package com.hcmute.tech_shop.services.Impl;

import com.hcmute.tech_shop.dtos.requests.BrandRequest;
import com.hcmute.tech_shop.entities.Brand;
import com.hcmute.tech_shop.repositories.BrandRepository;
import com.hcmute.tech_shop.repositories.ProductRepository;
import com.hcmute.tech_shop.services.interfaces.IBrandService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class BrandServiceImpl implements IBrandService {
    @Autowired
    BrandRepository brandRepository;

    @Autowired
    ProductRepository productRepository;

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
    private final Path root = Paths.get("./uploads");

    @Override
    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
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
    public List<Brand> findAll(Sort sort) {
        return brandRepository.findAll(sort);
    }

    private boolean isValidSuffixImage(String img) {
        return img.endsWith(".jpg") || img.endsWith(".jpeg") ||
                img.endsWith(".png") || img.endsWith(".gif") ||
                img.endsWith(".bmp");
    }

    public String saveImage(MultipartFile file) {
        try {
            // get file name
            String fileName = file.getOriginalFilename();
            // generate code random base on UUID
            String uniqueFileName = UUID.randomUUID().toString() + "_" + LocalDate.now() + "_" + fileName;
            Files.copy(file.getInputStream(), this.root.resolve(uniqueFileName), StandardCopyOption.REPLACE_EXISTING);
            return uniqueFileName;
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("Filename already exists.");
            }

            throw new RuntimeException(e.getMessage());
        }
    }

    public String updateImage(MultipartFile file, String filename) {
        try {
            if (deleteImage(filename)) {
                return saveImage(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return saveImage(file);
    }

    @Override
    public boolean deleteImage(String filename) {
        try {
            Path file = root.resolve(filename);
            return Files.deleteIfExists(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean addBrand(BrandRequest brandRequest, MultipartFile file){
        try {
            String img = "";
            if (file == null) {
                img = "/uploads/brand.jpg";
            } else {
                if (!isValidSuffixImage(Objects.requireNonNull(file.getOriginalFilename()))) {
                    return false;
                }
                img = saveImage(file);
            }
            Brand brand = new Brand();
            brand.setBrandImg(img);
            brand.setActive(true);
            brand.setName(brandRequest.getName());
            brandRepository.save(brand);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateBrand(BrandRequest brandRequest, Long id, String oldImg){
        try {
            Brand brand = brandRepository.findById(id).get();
            brand.setName(brandRequest.getName());
            brand.setBrandImg(oldImg);
            brand.setActive(brandRequest.isActive());
            brandRepository.save(brand);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateBrand(BrandRequest brandRequest, Long id, String oldImg, MultipartFile file){
        try {
            String img = "";
            if (file == null) {
                img = "/uploads/brand.jpg";
            }
            else {
                if (!isValidSuffixImage(Objects.requireNonNull(file.getOriginalFilename()))) {
                    throw new BadRequestException("Image is not valid");
                }
                deleteImage(oldImg);
                img = saveImage(file);
                Brand brand = brandRepository.findById(id).get();
                brand.setName(brandRequest.getName());
                brand.setBrandImg(img);
                brandRepository.save(brand);
                return true;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteBrand(Long brandId){
        try {
            Brand brand = brandRepository.findById(brandId).get();
            if(!productRepository.findByBrandName(brand.getName()).isEmpty()){
                brand.setActive(false);
                brandRepository.save(brand);
            }
            else {
                deleteImage(brand.getBrandImg());
                brandRepository.delete(brand);
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
