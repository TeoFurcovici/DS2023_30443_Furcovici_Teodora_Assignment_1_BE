package com.example.ds.EnergyUtilityPlatform.controller;

import com.example.ds.EnergyUtilityPlatform.model.Account;
import com.example.ds.EnergyUtilityPlatform.model.Device;
import com.example.ds.EnergyUtilityPlatform.model.RegularUser;
import com.example.ds.EnergyUtilityPlatform.model.dto.DTOAccount;
import com.example.ds.EnergyUtilityPlatform.service.AccountService;
import com.example.ds.EnergyUtilityPlatform.service.CipherDecrypt;
import com.example.ds.EnergyUtilityPlatform.service.DeviceService;
import com.example.ds.EnergyUtilityPlatform.service.RegularUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.OpInc;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "/demo")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    final String secretKey = "JHKLXABYZC!!!!";
    @Autowired
    private AccountService accountService;
    @Autowired
    private RegularUserService regularUserService;

    @Autowired
    private DeviceService deviceService;

    @PostMapping(path = "/addUser")
    public @ResponseBody String addNewUser(@RequestBody DTOAccount dtoUser) {
        CipherDecrypt cipherDecrypt = new CipherDecrypt();
        String encryptedPassUser = cipherDecrypt.encrypt(dtoUser.getPassword(), secretKey);
        if (encryptedPassUser != null) {
            LOGGER.info("Password  for user " + dtoUser.getEmail() + " was  successfully encrypted!!");
        }
        Account newAcc = new Account(dtoUser.getFirstName(), dtoUser.getLastName(), dtoUser.getEmail(), encryptedPassUser, dtoUser.getUsername(), dtoUser.getIsAdmin());
        if (accountService.findByUsername(dtoUser.getUsername())==null) {
            accountService.save(newAcc);
            RegularUser regularUser = new RegularUser(newAcc);
            newAcc.setUser(regularUser);
            regularUserService.save(regularUser);
            LOGGER.info("User " + dtoUser.getEmail() + " was found!!");

            return "Saved";
        }
        LOGGER.warn("User " + dtoUser.getEmail() + " already exists!!");

        return "Already exists user";
    }

    @GetMapping(path = "/findUserByUsername/{username}")
    public ResponseEntity<Account> findUserByUsername(@PathVariable String username) {
        Optional<Account> accountUser = accountService.findByUsername(username);
        CipherDecrypt cipherDecrypt = new CipherDecrypt();
        String decryptPass = cipherDecrypt.decrypt(accountUser.get().getPassword(), secretKey);
        if (decryptPass != null) {
            LOGGER.info("Password  for user " + accountUser.get().getEmail() + " was  successfully decrypted!!");
        }
        accountUser.get().setPassword(decryptPass);
        LOGGER.info("User " + accountUser.get().getEmail() + " was found!!");

        return ResponseEntity.status(HttpStatus.OK).body(accountUser.orElse(new Account()));
    }

    @GetMapping(path = "/getAllDevicesForUser/{username}")
    public List<Device> getAllDevicesByUsername(@PathVariable String username) {
        Iterable<Device> allDevices=deviceService.findAllDevices();
        List<Device> allDeviceForSpecificUser=new ArrayList<>();
        Optional<Account> accountUser=accountService.findByUsername(username);
        Integer regularUserId= accountUser.get().getUser().getUserId();
        for (Device currentDevice:allDevices) {
            if (currentDevice.getRegularUser() != null) {
                if (Objects.equals(currentDevice.getRegularUser().getUserId(), regularUserId))
                    allDeviceForSpecificUser.add(currentDevice);
            }
        }

        return  allDeviceForSpecificUser;
    }

    @GetMapping(path = "/getAllAvailableDevices")
    public List<Device> getAllAvailableDevices() {
        Iterable<Device> allDevices=deviceService.findAllDevices();
        List<Device> allDeviceForSpecificUser=new ArrayList<>();
        for (Device currentDevice:allDevices) {
            if(currentDevice.getRegularUser()==null)
                allDeviceForSpecificUser.add(currentDevice);
        }

        return  allDeviceForSpecificUser;
    }
}
