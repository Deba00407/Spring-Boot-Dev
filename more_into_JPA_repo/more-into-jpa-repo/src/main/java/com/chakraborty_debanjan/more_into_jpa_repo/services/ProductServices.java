package com.chakraborty_debanjan.more_into_jpa_repo.services;

import com.chakraborty_debanjan.more_into_jpa_repo.custom_errors.ConflictException;
import com.chakraborty_debanjan.more_into_jpa_repo.custom_errors.OperationException;
import com.chakraborty_debanjan.more_into_jpa_repo.custom_errors.ResourceNotFoundException;
import com.chakraborty_debanjan.more_into_jpa_repo.custom_errors.ViolationOfDBConstraintsException;
import com.chakraborty_debanjan.more_into_jpa_repo.dtos.ProductDTO;
import com.chakraborty_debanjan.more_into_jpa_repo.entities.ProductEntity;
import com.chakraborty_debanjan.more_into_jpa_repo.repositories.ProductRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

interface ProductServicesInterface {
    ProductDTO getProductById(String id);
    List<ProductDTO> getAllProducts();
    void saveProduct(ProductDTO product);
    void deleteProduct(UUID id);
}

@Service
public class ProductServices implements ProductServicesInterface{
    private final ProductRepo productRepo;

    private final ModelMapper modelMapper;

    public ProductServices(ProductRepo productRepo, ModelMapper modelMapper) {
        this.productRepo = productRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public void deleteProduct(UUID id) {
        // check if the product exists
        Optional<ProductEntity> product = productRepo.findById(id);
        if(product.isEmpty()){
            throw new ResourceNotFoundException(String.format("Product with id %s not found", id));
        }

        // only delete it if the product exists
        productRepo.deleteById(id);
    }

    @Override
    @Transactional(rollbackOn = ViolationOfDBConstraintsException.class)
    public void saveProduct(ProductDTO product) {
        // check if the product already exits
        Optional<ProductEntity> existing_product = productRepo.findBySku(product.getSku());

        if(existing_product.isPresent()){
           // product already exists
            throw new ConflictException(String.format("Product with sku %s already exists", product.getSku()));
        }

        try{
            productRepo.save(modelMapper.map(product, ProductEntity.class));
        }catch (Exception e){
            throw new OperationException("Product could not be saved to database");
        }
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<ProductEntity> products = productRepo.findAll();

        return products
                .stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
    }

    @Override
    public ProductDTO getProductById(String id) {
        Optional<ProductEntity> re = productRepo.findById(UUID.fromString(id));
        if(re.isEmpty()){
            throw new ResourceNotFoundException("Product not found");
        }
        return modelMapper.map(re.get(), ProductDTO.class);
    }
}
