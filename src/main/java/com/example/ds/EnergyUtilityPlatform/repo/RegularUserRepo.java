package com.example.ds.EnergyUtilityPlatform.repo;

import com.example.ds.EnergyUtilityPlatform.model.RegularUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RegularUserRepo extends CrudRepository<RegularUser,Integer> {
    Optional<RegularUser> findByUserId(Integer id);
    void deleteByUserId(Integer userId);
}