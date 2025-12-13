package com.debanjan.e_commerce.services;

import com.debanjan.e_commerce.DTO.CreateProductDTO;
import com.debanjan.e_commerce.DTO.ProductResponseDTO;
import com.debanjan.e_commerce.DTO.ProductUpdateDTO;
import com.debanjan.e_commerce.custom_exceptions.BadRequestException;
import com.debanjan.e_commerce.custom_exceptions.DuplicateResourceException;
import com.debanjan.e_commerce.custom_exceptions.OperationException;
import com.debanjan.e_commerce.custom_exceptions.ResourceNotFoundException;
import com.debanjan.e_commerce.entities.ProductEntity;
import com.debanjan.e_commerce.repositories.ProductEntityRepository;
import com.debanjan.e_commerce.utils.ValidateURL;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

interface ProductServiceInterface {
    ProductResponseDTO saveProduct(CreateProductDTO productDTO);
    List<ProductResponseDTO> getAllProducts();
    ProductResponseDTO getProductById(Long id);
    ProductResponseDTO getProductByProductCode(String productCode);
    void deleteProductById(Long id);
    void deleteProductByProductCode(String productCode);
    CreateProductDTO updateProductByProductCode(String productCode, ProductUpdateDTO productDTO);
    CreateProductDTO updateProductById(Long id, ProductUpdateDTO productDTO);
}

@Service
@Validated
public class ProductService implements ProductServiceInterface{
    private final ProductEntityRepository productEntityRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ProductService(ProductEntityRepository productEntityRepository) {
        this.productEntityRepository = productEntityRepository;
    }

    private ProductEntity convertToProductEntity(CreateProductDTO productDTO){
        ProductEntity pe = new ProductEntity();
        pe.setProductPrice(productDTO.getProductPrice());
        pe.setProductCategory(productDTO.getProductCategory());
        pe.setProductName(productDTO.getProductName());
        pe.setProductDescription(productDTO.getProductDescription());
        pe.setProductCoverImage(productDTO.getProductCoverImage());
        pe.setProductStock(productDTO.getProductStock());
        return pe;
    }

    @Override
    @Transactional
    public ProductResponseDTO saveProduct(CreateProductDTO productDTO){
        // validate the cover image url
        if(!ValidateURL.isValid(productDTO.getProductCoverImage())){
            throw new BadRequestException("Invalid cover image URL: " + productDTO.getProductCoverImage());
        }

        ProductEntity pe = convertToProductEntity(productDTO);

        if(productEntityRepository.existsByProductCode(pe.getProductCode())){
            throw new DuplicateResourceException("Product already exists with product code: " + pe.getProductCode());
        }

        ProductEntity saved = productEntityRepository.save(pe);

        return modelMapper.map(saved, ProductResponseDTO.class);
    }

    @Override
    @Transactional
    public List<ProductResponseDTO> getAllProducts(){
        List<ProductEntity> products = productEntityRepository.findAll();
        if(products.isEmpty()) return List.of();

        return products
                .stream()
                .map(productEntity -> modelMapper.map(productEntity, ProductResponseDTO.class))
                .toList();
    }

    @Override
    @Transactional
    public ProductResponseDTO getProductById(Long id) {
        Optional<ProductEntity> productEntity = productEntityRepository.findById(id);
        if(productEntity.isEmpty()){
            throw new ResourceNotFoundException("Product not found for id: " + id);
        }

        return modelMapper.map(productEntity.get(), ProductResponseDTO.class);
    }

    @Override
    @Transactional
    public ProductResponseDTO getProductByProductCode(String productCode) {

        Optional<ProductEntity> productEntity = productEntityRepository.findByProductCode(productCode);
        if(productEntity.isEmpty()){
            throw new ResourceNotFoundException("Product not found for product code: " + productCode);
        }

        return modelMapper.map(productEntity.get(), ProductResponseDTO.class);
    }

    @Override
    @Transactional
    public void deleteProductById(Long id) {
        Optional<ProductEntity> productEntity = productEntityRepository.findById(id);
        if(productEntity.isEmpty()){
            throw new ResourceNotFoundException("Product not found for id: " + id);
        }

        productEntityRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteProductByProductCode(String productCode) {
        if(!productEntityRepository.existsByProductCode(productCode)){
            throw new ResourceNotFoundException("Product not found for product code: " + productCode);
        }

        productEntityRepository.deleteByProductCode(productCode);
    }

    private void fieldUpdater(ProductEntity product, ProductUpdateDTO src){
        // allow updation of only whitelisted properties
        if(src.getProductDescription() != null) product.setProductDescription(src.getProductDescription());
        if(src.getProductPrice() != null) product.setProductPrice(src.getProductPrice());
        if(src.getProductCoverImage() != null) product.setProductCoverImage(src.getProductCoverImage());
        if(src.getProductCategory() != null) product.setProductCategory(src.getProductCategory());
        if(src.getProductName() != null) product.setProductName(src.getProductName());
        if(src.getProductPrice() != null) product.setProductPrice(src.getProductPrice());
    }

    @Override
    @Transactional
    public CreateProductDTO updateProductByProductCode(String productCode, ProductUpdateDTO dto) {
        ProductEntity product = productEntityRepository.findByProductCode(productCode)
                            .orElseThrow(() -> new ResourceNotFoundException("Product not found for productCode: " + productCode));

        fieldUpdater(product, dto);

        ProductEntity updated = productEntityRepository.save(product);

        return modelMapper.map(updated, CreateProductDTO.class);
    }

    @Override
    @Transactional
    public CreateProductDTO updateProductById(Long id, ProductUpdateDTO dto) {
        ProductEntity product = productEntityRepository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("Product not found for ID: " + id));

        fieldUpdater(product, dto);

        ProductEntity updated = productEntityRepository.save(product);

        return modelMapper.map(updated, CreateProductDTO.class);
    }
}
