package com.chakraborty_debanjan.more_into_jpa_repo.services;

import com.chakraborty_debanjan.more_into_jpa_repo.custom_errors.OperationException;
import com.chakraborty_debanjan.more_into_jpa_repo.custom_errors.ResourceNotFoundException;
import com.chakraborty_debanjan.more_into_jpa_repo.dtos.SellerDTO;
import com.chakraborty_debanjan.more_into_jpa_repo.entities.SellerEntity;
import com.chakraborty_debanjan.more_into_jpa_repo.repositories.SellerRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

interface SellerServicesInterface {
    void registerNewSeller(SellerDTO new_seller);
    SellerDTO getSellerById(Long id);
    void deleteSeller(Long id);
    List<SellerDTO> getAllSellers();
}

@Service
public class SellerServices implements SellerServicesInterface {

    private final SellerRepo sellerRepo;
    private final ModelMapper modelMapper;

    public SellerServices(SellerRepo sellerRepo, ModelMapper modelMapper) {
        this.sellerRepo = sellerRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public void registerNewSeller(SellerDTO new_seller) {
        SellerEntity se = modelMapper.map(new_seller, SellerEntity.class);

        // Prevent ModelMapper from copying ID
        se.setSellerId(null);

        try {
            sellerRepo.save(se);
        } catch (Exception e) {
            throw new OperationException("Error while saving seller: " + e.getMessage());
        }
    }


    @Override
    @Transactional
    public SellerDTO getSellerById(Long id) {
        try{
            SellerEntity se = sellerRepo.findById(id).orElseThrow();
            return modelMapper.map(se, SellerDTO.class);
        }catch (NoSuchElementException e){
            throw new ResourceNotFoundException(String.format("Seller with id %s not found", id));
        }
    }


    @Override
    @Transactional
    public void deleteSeller(Long id) {
        try {
            sellerRepo.deleteById(id);
        }catch (Exception e){
            throw new ResourceNotFoundException(String.format("Seller with id %s not found", id));
        }
    }

    @Override
    @Transactional
    public List<SellerDTO> getAllSellers() {
        List<SellerEntity> sellers = sellerRepo.findAll();

        return sellers.stream().map(se -> modelMapper.map(se, SellerDTO.class)).toList();
    }
}
