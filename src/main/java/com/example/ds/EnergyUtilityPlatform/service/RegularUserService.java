package com.example.ds.EnergyUtilityPlatform.service;

import com.example.ds.EnergyUtilityPlatform.model.Device;
import com.example.ds.EnergyUtilityPlatform.model.RegularUser;
import com.example.ds.EnergyUtilityPlatform.repo.RegularUserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RegularUserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegularUserService.class);
    @Autowired
    RegularUserRepo regularUserRepo;

    public RegularUserService(RegularUserRepo regularUserRepo) {
        this.regularUserRepo = regularUserRepo;
    }

    public RegularUser save(RegularUser regularUser) {
        LOGGER.info("*** From RegularUserService *** User " + regularUser.getUserAccount().getUsername() + " was saved!");
        return regularUserRepo.save(regularUser);
    }

    public Optional<RegularUser> findById(Integer id) {
        Optional<RegularUser> regularUser = regularUserRepo.findById(id);
        if (regularUser.isPresent()) {
            LOGGER.info("*** From  UserService *** User  " + regularUser.get().getUserAccount().getUsername() + " was found!");
            return regularUser;

        } else {
            LOGGER.error("*** From  UserService *** User " + regularUser.get().getUserAccount().getUsername() + " was not found!");
            return null;
        }
    }
    @Transactional
    public void deleteByUserId(Integer adminId) {
        LOGGER.info(" *** From AdminService *** Admin " + adminId + " was deleted!");
        regularUserRepo.deleteByUserId(adminId);
    }
    public Iterable<RegularUser> findAllUsers()
    {
        return regularUserRepo.findAll();
    }
}
