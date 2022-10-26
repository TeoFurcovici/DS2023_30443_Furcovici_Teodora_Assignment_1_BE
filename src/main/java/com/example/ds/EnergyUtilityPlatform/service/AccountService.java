package com.example.ds.EnergyUtilityPlatform.service;

import com.example.ds.EnergyUtilityPlatform.model.Account;
import com.example.ds.EnergyUtilityPlatform.model.RegularUser;
import com.example.ds.EnergyUtilityPlatform.repo.AccountRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);
    @Autowired
    private AccountRepo accountRepo;

    public AccountService(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    public Account save(Account account) {
        LOGGER.info(" *** From AccountService *** Account " + account.getUsername() + " was saved!");
        return accountRepo.save(account);
    }

    public Optional<Account> findByUsername(String username) {
        Optional<Account> account = accountRepo.findByUsername(username);
        if (account.isPresent()) {
            LOGGER.info("*** From AccountService *** Account " + username + " was found");
            return account;

        } else {
            LOGGER.error("*** From AccountService *** Account " + username + " was not found!");
            return null;
        }
    }

    @Transactional
    public void deleteByAccountId(Integer adminId) {
        LOGGER.info(" *** From AccountService *** Admin " + adminId + " was deleted!");
        accountRepo.deleteByAccountId(adminId);
    }
    public List<Account> findAllByIsAdmin(String isAdmin)
    {
       return accountRepo.findAllByIsAdmin(isAdmin);
    }
    public RegularUser findByUserId(Integer userId)
    {
        return  accountRepo.findByUserUserId(userId);
    }
}