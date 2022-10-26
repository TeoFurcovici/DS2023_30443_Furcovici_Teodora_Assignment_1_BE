package com.example.ds.EnergyUtilityPlatform.repo;

import com.example.ds.EnergyUtilityPlatform.model.Admin;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface AdminRepo extends CrudRepository<Admin,Integer> {
    Optional<Admin> findByUserId(Integer userId);
    void deleteByUserId(Integer userId);
}