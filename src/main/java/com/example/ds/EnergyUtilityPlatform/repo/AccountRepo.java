package com.example.ds.EnergyUtilityPlatform.repo;

import com.example.ds.EnergyUtilityPlatform.model.Account;
import com.example.ds.EnergyUtilityPlatform.model.RegularUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepo extends CrudRepository<Account,Integer> {
    Optional<Account> findByUsername(String username);
    void deleteByAccountId(Integer accountId);
    List<Account> findAllByIsAdmin(String isAdmin);
    RegularUser findByUserUserId(Integer userId);
}
