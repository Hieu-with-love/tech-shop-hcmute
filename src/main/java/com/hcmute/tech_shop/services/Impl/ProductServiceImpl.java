package com.hcmute.tech_shop.services.Impl;

import com.hcmute.tech_shop.builder.ProductFilterBuilder;
import com.hcmute.tech_shop.convert.ProductFilterBuilderConverter;
import com.hcmute.tech_shop.dtos.requests.ProductRequest;
import com.hcmute.tech_shop.dtos.responses.ProductResponse;
import com.hcmute.tech_shop.entities.Brand;
import com.hcmute.tech_shop.entities.Category;
import com.hcmute.tech_shop.entities.Product;
import com.hcmute.tech_shop.repositories.BrandRepository;
import com.hcmute.tech_shop.repositories.CategoryRepository;
import com.hcmute.tech_shop.repositories.ProductRepository;
import com.hcmute.tech_shop.repositories.custome.ProductRepositoryCustom;
import com.hcmute.tech_shop.services.interfaces.IProductService;
import com.hcmute.tech_shop.utils.Constant;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    BrandRepository brandRepository;

    private final Path root = Paths.get("./uploads");
    @Autowired
    private ProductFilterBuilderConverter productFilterBuilderConverter;

    @Override
    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public List<ProductResponse> getAllProducts(List<Product> products) {
        return products.stream().map(p -> {
            String oldPrice = Constant.formatter.format(p.getPrice().add(BigDecimal.valueOf(2000000)));
            return ProductResponse.builder()
                    .id(p.getId())
                    .name(p.getName())
                    .price(Constant.formatter.format(p.getPrice()))
                    .oldPrice(oldPrice)
                    .thumbnail(p.getThumbnail())
                    .build();
        }).toList();
    }

    @Override
    public List<ProductResponse> filterProducts(Map<String, Object> params) {
        ProductFilterBuilder builder = productFilterBuilderConverter.toProductFilterBuilder(params);

        List<Product> productEntities =productRepository.findAll(
                ProductRepositoryCustom.filter(builder)
        );

        return getAllProducts(productEntities);
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

    @Override
    public boolean deleteImage(String filename) {
        try {
            java.nio.file.Path oldImage = Paths.get("uploads/" + filename);
            if (!oldImage.getFileName().equals(filename) && Files.exists(oldImage)) {
                Files.delete(oldImage);
                return true;
            }
        } catch (IOException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
        return false;
    }

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public boolean createProduct(ProductRequest productRequest, MultipartFile file) throws IOException {
        Category categoryExisting = categoryRepository.findById(productRequest.getCategoryId()).get();
        Brand brand = brandRepository.findById(productRequest.getBrandId()).get();
        try {
            String thumbnail = "";
            if (file == null) {
                thumbnail = "default-product.jpg";
            } else {
                if (!isValidSuffixImage(Objects.requireNonNull(file.getOriginalFilename()))) {
                    throw new BadRequestException("Image is not valid");
                }
                thumbnail = saveImage(file);
            }
            Product product = Product.builder()
                    .name(productRequest.getName())
                    .description(productRequest.getDescription())
                    .price(productRequest.getPrice())
                    .cpu(productRequest.getCpu())
                    .ram(productRequest.getRam())
                    .os(productRequest.getOs())
                    .monitor(productRequest.getMonitor())
                    .battery(productRequest.getBattery())
                    .graphicCard(productRequest.getGraphicCard())
                    .port(productRequest.getPort())
                    .rearCamera(productRequest.getRearCamera())
                    .frontCamera(productRequest.getFrontCamera())
                    .stockQuantity(productRequest.getStockQuantity())
                    .warranty(productRequest.getWarranty())
                    .weight(productRequest.getWeight())
                    .thumbnail(thumbnail)
                    .category(categoryExisting)
                    .brand(brand)
                    .build();
            productRepository.save(product);
            return true;
        } catch (IOException ioe) {
            throw new IOException("Cannot create product" + ioe.getMessage());
        }
    }

    private boolean isValidSuffixImage(String img) {
        return img.endsWith(".jpg") || img.endsWith(".jpeg") ||
                img.endsWith(".png") || img.endsWith(".gif") ||
                img.endsWith(".bmp");
    }

    @Override
    public Product updateProduct(Long productId, ProductRequest productRequest) throws IOException {
        Brand brandExisting = brandRepository.findById(productRequest.getBrandId()).get();
        // get product old by id
        Product existingProduct = productRepository.findById(productId).get();
        existingProduct.setName(productRequest.getName());
        existingProduct.setDescription(productRequest.getDescription());
        existingProduct.setPrice(productRequest.getPrice());
        existingProduct.setCpu(productRequest.getCpu());
        existingProduct.setRam(productRequest.getRam());
        existingProduct.setOs(productRequest.getOs());
        existingProduct.setMonitor(productRequest.getMonitor());
        existingProduct.setBattery(productRequest.getBattery());
        existingProduct.setGraphicCard(productRequest.getGraphicCard());
        existingProduct.setPort(productRequest.getPort());
        existingProduct.setRearCamera(productRequest.getRearCamera());
        existingProduct.setFrontCamera(productRequest.getFrontCamera());
        existingProduct.setStockQuantity(productRequest.getStockQuantity());
        existingProduct.setWarranty(productRequest.getWarranty());
        existingProduct.setWeight(productRequest.getWeight());
        existingProduct.setBrand(brandExisting);
        // create if chua ton tai, update if ton tai
        return productRepository.save(existingProduct);
    }

    @Override
    public Product updateProduct(Long productId, ProductRequest productRequest, MultipartFile file) throws IOException {
        Brand brandExisting = brandRepository.findById(productRequest.getBrandId()).get();
        Product existingProduct = productRepository.findById(productId).get();

        String thumbnail = "";
        if (file == null) {
            thumbnail = "/uploads/default-product.jpg";
        } else {
            if (!isValidSuffixImage(Objects.requireNonNull(file.getOriginalFilename()))) {
                throw new BadRequestException("Image is not valid");
            }
//            deleteImage(existingProduct.getThumbnail());
            thumbnail = saveImage(file);
        }
        // get product old by id
        existingProduct.setName(productRequest.getName());
        existingProduct.setDescription(productRequest.getDescription());
        existingProduct.setPrice(productRequest.getPrice());
        existingProduct.setCpu(productRequest.getCpu());
        existingProduct.setRam(productRequest.getRam());
        existingProduct.setOs(productRequest.getOs());
        existingProduct.setMonitor(productRequest.getMonitor());
        existingProduct.setBattery(productRequest.getBattery());
        existingProduct.setGraphicCard(productRequest.getGraphicCard());
        existingProduct.setPort(productRequest.getPort());
        existingProduct.setRearCamera(productRequest.getRearCamera());
        existingProduct.setFrontCamera(productRequest.getFrontCamera());
        existingProduct.setStockQuantity(productRequest.getStockQuantity());
        existingProduct.setWarranty(productRequest.getWarranty());
        existingProduct.setWeight(productRequest.getWeight());
        existingProduct.setThumbnail(thumbnail);
        existingProduct.setBrand(brandExisting);
        // create if chua ton tai, update if ton tai
        return productRepository.save(existingProduct);
    }

    @Override
    public void deleteProduct(Long productId) {
        Product product = findById(productId).get();
        deleteImage(product.getThumbnail());
        productRepository.delete(product);
    }

    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1%")
    @Override
    public Product findByName(String name) {
        return productRepository.findByName(name);
    }

    @Query("SELECT p FROM Product p WHERE p.cpu LIKE %?1%")
    @Override
    public List<Product> findByCpu(String cpu) {
        return productRepository.findByCpu(cpu);
    }

    @Query("SELECT p FROM Product p WHERE p.ram LIKE %?1%")
    @Override
    public List<Product> findByRam(String ram) {
        return productRepository.findByRam(ram);
    }

    @Query("SELECT p FROM Product p WHERE p.os LIKE %?1%")
    @Override
    public List<Product> findByOs(String os) {
        return productRepository.findByOs(os);
    }

    @Query("SELECT p FROM Product p WHERE p.monitor LIKE %?1%")
    @Override
    public List<Product> findByMonitor(String monitor) {
        return productRepository.findByMonitor(monitor);
    }

    @Query("SELECT p FROM Product p WHERE p.weight = ?1")
    @Override
    public List<Product> findByWeight(Double weight) {
        return productRepository.findByWeight(weight);
    }

    @Query("SELECT p FROM Product p WHERE p.battery LIKE %?1%")
    @Override
    public List<Product> findByBattery(String battery) {
        return productRepository.findByBattery(battery);
    }

    @Query("SELECT p FROM Product p WHERE p.graphicCard LIKE %?1%")
    @Override
    public List<Product> findByGraphicCard(String graphicCard) {
        return productRepository.findByGraphicCard(graphicCard);
    }

    @Query("SELECT p FROM Product p WHERE p.port LIKE %?1%")
    @Override
    public List<Product> findByPort(String port) {
        return productRepository.findByPort(port);
    }

    @Query("SELECT p FROM Product p WHERE p.rearCamera LIKE %?1%")
    @Override
    public List<Product> findByRearCamera(String rearCamera) {
        return productRepository.findByRearCamera(rearCamera);
    }

    @Query("SELECT p FROM Product p WHERE p.frontCamera LIKE %?1%")
    @Override
    public List<Product> findByFrontCamera(String frontCamera) {
        return productRepository.findByFrontCamera(frontCamera);
    }

    @Query("SELECT p FROM Product p WHERE p.stockQuantity = ?1")
    @Override
    public List<Product> findByStockQuantity(int stockQuantity) {
        return productRepository.findByStockQuantity(stockQuantity);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public <S extends Product> S save(S entity) {
        return productRepository.save(entity);
    }

    @Override
    public Optional<Product> findById(Long aLong) {
        return productRepository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return productRepository.existsById(aLong);
    }

    @Override
    public long count() {
        return productRepository.count();
    }

    @Override
    public void deleteById(Long aLong) {
        productRepository.deleteById(aLong);
    }

    @Override
    public List<ProductRequest> findByCategoryName(String categoryName) {
        List<ProductRequest> productDTOList = new ArrayList<>();
        List<Product> products = productRepository.findByCategoryName(categoryName);
        for (Product product : products) {
            ProductRequest productDTO = new ProductRequest();
            BeanUtils.copyProperties(product, productDTO);
            productDTO.setImg(product.getThumbnail());
            productDTOList.add(productDTO);
        }
        return productDTOList;
    }

    @Override
    public Page<Product> getAllProducts(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> getAllSortedProducts(int pageNumber, int pageSize, Sort sort) {
        return null;
    }

}
