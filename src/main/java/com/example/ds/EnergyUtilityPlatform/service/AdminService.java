package com.example.ds.EnergyUtilityPlatform.service;

import com.example.ds.EnergyUtilityPlatform.model.Admin;
import com.example.ds.EnergyUtilityPlatform.repo.AdminRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class  AdminService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminService.class);
    final String secretKey = "JHKLXABYZC!!!!";
    @Autowired
    private AdminRepo adminRepo;

    public AdminService(AdminRepo adminRepo) {
        this.adminRepo = adminRepo;
    }

    public Admin save(Admin admin) {
        LOGGER.info(" *** From AdminService *** Admin " + admin.getUserAccount().getUsername() + " was deleted!");
        return adminRepo.save(admin);
    }

    public Optional<Admin> findById(Integer id) {
        Optional<Admin> admin = adminRepo.findById(id);
        if (admin.isPresent()) {
            LOGGER.info(" *** From AdminService *** Admin " + admin.get().getUserAccount().getUsername() + " was found!");
            return admin;
        } else {
            LOGGER.error(" *** From AdminService *** Admin " + admin.get().getUserAccount().getUsername() + " was not found!");
            return null;
        }
    }
    @Transactional
    public void deleteById(Integer adminId) {
        LOGGER.info(" *** From AdminService *** Admin " + adminId + " was deleted!");
        adminRepo.deleteByUserId(adminId);
    }
}