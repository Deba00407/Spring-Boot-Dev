package com.debanjan.e_commerce.services;

import com.debanjan.e_commerce.DTO.ProductDTO;
import com.debanjan.e_commerce.DTO.ProductUpdateDTO;
import com.debanjan.e_commerce.custom_exceptions.BadRequestException;
import com.debanjan.e_commerce.custom_exceptions.OperationException;
import com.debanjan.e_commerce.custom_exceptions.ResourceNotFoundException;
import com.debanjan.e_commerce.entities.ProductEntity;
import com.debanjan.e_commerce.repositories.ProductEntityRepository;
import com.debanjan.e_commerce.utils.ValidateURL;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

interface ProductServiceInterface {
    ProductDTO saveProduct(ProductDTO productDTO);
    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(Long id);
    ProductDTO getProductByProductCode(String productCode);
    void deleteProductById(Long id);
    void deleteProductByProductCode(String productCode);
    ProductDTO updateProductByProductCode(String productCode, ProductUpdateDTO productDTO);
    ProductDTO updateProductById(Long id, ProductUpdateDTO productDTO);
}

@Service
public class ProductService implements ProductServiceInterface{
    private final ProductEntityRepository productEntityRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ProductService(ProductEntityRepository productEntityRepository) {
        this.productEntityRepository = productEntityRepository;
    }

    @Override
    @Transactional
    public ProductDTO saveProduct(@Validated ProductDTO productDTO){

        // validate the cover image url
        if(!ValidateURL.isValid(productDTO.getProductCoverImage())){
            throw new BadRequestException("Invalid cover image URL: " + productDTO.getProductCoverImage());
        }

        // convert to a product entity and save
        productEntityRepository.findByProductCode(productDTO.getProductCode()).ifPresentOrElse(
                existing -> {
                    throw new OperationException("Product already exists with product code: " + productDTO.getProductCode());
                },
                () -> productEntityRepository.save(modelMapper.map(productDTO, ProductEntity.class))
        );

        return productDTO;
    }

    @Override
    @Transactional
    public List<ProductDTO> getAllProducts(){
        List<ProductEntity> products = productEntityRepository.findAll();
        if(products.isEmpty()) return List.of();

        return products
                .stream()
                .map(productEntity -> modelMapper.map(productEntity, ProductDTO.class))
                .toList();
    }

    @Override
    @Transactional
    public ProductDTO getProductById(Long id) {
        Optional<ProductEntity> productEntity = productEntityRepository.findById(id);
        if(productEntity.isEmpty()){
            throw new ResourceNotFoundException("Product not found for id: " + id);
        }

        return modelMapper.map(productEntity.get(), ProductDTO.class);
    }

    @Override
    @Transactional
    public ProductDTO getProductByProductCode(String productCode) {
        return null;
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
    public ProductDTO updateProductByProductCode(String productCode, @Validated ProductUpdateDTO dto) {
        ProductEntity product = productEntityRepository.findByProductCode(productCode)
                            .orElseThrow(() -> new ResourceNotFoundException("Product not found for productCode: " + productCode));

        fieldUpdater(product, dto);

        ProductEntity updated = productEntityRepository.save(product);

        return modelMapper.map(updated, ProductDTO.class);
    }

    @Override
    @Transactional
    public ProductDTO updateProductById(Long id, ProductUpdateDTO dto) {
        ProductEntity product = productEntityRepository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("Product not found for ID: " + id));

        fieldUpdater(product, dto);

        ProductEntity updated = productEntityRepository.save(product);

        return modelMapper.map(updated, ProductDTO.class);
    }
}
