package com.example.ds.EnergyUtilityPlatform.controller;

import com.example.ds.EnergyUtilityPlatform.model.Account;
import com.example.ds.EnergyUtilityPlatform.model.Admin;
import com.example.ds.EnergyUtilityPlatform.model.Device;
import com.example.ds.EnergyUtilityPlatform.model.RegularUser;
import com.example.ds.EnergyUtilityPlatform.model.dto.DTOAccount;
import com.example.ds.EnergyUtilityPlatform.model.dto.DTOAccountForUpdate;
import com.example.ds.EnergyUtilityPlatform.model.dto.DTODevice;
import com.example.ds.EnergyUtilityPlatform.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "/demo")
public class AdminController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
    final String secretKey = "JHKLXABYZC!!!!";
    @Autowired
    private AccountService accountService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private DeviceService deviceService;

    @Autowired
    private RegularUserService regularUserService;

    @PostMapping(path = "/addAdmin")
    public @ResponseBody String addNewAdmin(@RequestBody DTOAccount dtoUser) {
        CipherDecrypt cipherDecrypt = new CipherDecrypt();
        String encryptedPass = cipherDecrypt.encrypt(dtoUser.getPassword(), secretKey);
        if (encryptedPass != null) {
            LOGGER.info("Password  for admin " + dtoUser.getEmail() + " was  successfully encrypted!!");
        }
        Account newAcc = new Account(dtoUser.getFirstName(), dtoUser.getLastName(), dtoUser.getEmail(), encryptedPass, dtoUser.getUsername(), dtoUser.getIsAdmin());
        if (accountService.findByUsername(dtoUser.getUsername()) == null) {
            accountService.save(newAcc);
            Admin admin = new Admin(newAcc);
            newAcc.setUser(admin);
            adminService.save(admin);
            LOGGER.info("Admin " + dtoUser.getEmail() + " was found!!");
            return "Saved";
        }
        LOGGER.warn("Admin " + dtoUser.getEmail() + " already exists!!");
        return "Already exists user";
    }

    @GetMapping(path = "/findUserByUsernameAdmin/{username}")
    public ResponseEntity<Admin> findUserByUsername(@PathVariable String username) {
        Optional<Account> accountAdmin = accountService.findByUsername(username);
        CipherDecrypt cipherDecrypt = new CipherDecrypt();
        String decryptPass = cipherDecrypt.decrypt(accountAdmin.get().getPassword(), secretKey);
        if (decryptPass != null) {
            LOGGER.info("Password  for admin " + accountAdmin.get().getEmail() + " was  successfully decrypted!!");
        }
        accountAdmin.get().setPassword(decryptPass);
        Integer adminId = accountAdmin.get().getUser().getUserId();
        Optional<Admin> admin = adminService.findById(adminId);
        LOGGER.info("Admin " + accountAdmin.get().getEmail() + " was found!!");

        return ResponseEntity.status(HttpStatus.OK).body(admin.orElse(new Admin()));

    }

    @PutMapping(path = "/updateAdmin/{username}")
    public @ResponseBody ResponseEntity<Account> updateAdmin(@RequestBody DTOAccountForUpdate account, @PathVariable String username) {
        Optional<Account> accountAdmin = accountService.findByUsername(username);
        if (accountAdmin.isPresent()) {
            LOGGER.info("Admin account  " + accountAdmin.get().getUsername() + " was found!");
            accountAdmin.get().setEmail(account.getEmail());
            accountAdmin.get().setUsername(account.getUsername());
            accountAdmin.get().setFirstName(account.getFirstName());
            accountAdmin.get().setLastName(account.getLastName());
            accountService.save(accountAdmin.get());
        }

        return ResponseEntity.status(HttpStatus.OK).body(accountAdmin.orElse(new Account()));
    }
    @PutMapping(path = "/updateUser/{username}")
    public @ResponseBody ResponseEntity<Account> updateUser(@RequestBody DTOAccountForUpdate account, @PathVariable String username) {
        Optional<Account> accountUser = accountService.findByUsername(username);
        if (accountUser.isPresent()) {
            LOGGER.info("User account  " + accountUser.get().getUsername() + " was found!");
            accountUser.get().setEmail(account.getEmail());
            accountUser.get().setUsername(account.getUsername());
            accountUser.get().setFirstName(account.getFirstName());
            accountUser.get().setLastName(account.getLastName());
            accountService.save(accountUser.get());
        }

        return ResponseEntity.status(HttpStatus.OK).body(accountUser.orElse(new Account()));
    }

    @Transactional
    @DeleteMapping(path = "/deleteAdmin/{username}")
    public @ResponseBody void deleteAdmin(@PathVariable String username) {
        Optional<Account> accountAdmin = accountService.findByUsername(username);
        adminService.deleteById(accountAdmin.get().getUser().getUserId());
        accountService.deleteByAccountId(accountAdmin.get().getAccountId());
    }

    @Transactional
    @DeleteMapping(path = "/deleteUser/{username}")
    public @ResponseBody void deleteUser(@PathVariable String username) {
        Optional<Account> accountAdmin = accountService.findByUsername(username);
        regularUserService.deleteByUserId(accountAdmin.get().getUser().getUserId());
        accountService.deleteByAccountId(accountAdmin.get().getAccountId());
    }

    @PostMapping(path = "/addDevice")
    public @ResponseBody
    String addDevice(@RequestBody DTODevice dtoDevice) {
        Device device = new Device(dtoDevice.getDescription(), dtoDevice.getAddress(), dtoDevice.getMaxHourEnergyConsumption());
        if (deviceService.findByAddressAndDescription(dtoDevice.getAddress(), dtoDevice.getDescription())==null) {
            deviceService.save(device);
            deviceService.save(device);
            LOGGER.info("Device at address  " + device.getAddress() + " was successfully added!");
            return "Saved device";
        }
        LOGGER.warn("Device at address  " + device.getAddress() + " already exists!");
        return "Already exists device";
    }

    @GetMapping(path = "/findDeviceByAddress/{address}/{description}")
    public ResponseEntity<Device> findDeviceByAddress(@PathVariable String address,@PathVariable String description) {
        Optional<Device> device = deviceService.findByAddressAndDescription(address,description);
        LOGGER.info("Device at address  " + device.get().getAddress() + " was found!!");

        return ResponseEntity.status(HttpStatus.OK).body(device.orElse(new Device()));
    }

    @PutMapping(path = "/updateDevice/{address}/{maxEnergy}")
    public @ResponseBody ResponseEntity<Device> updateDevice(@RequestBody DTODevice dtoDevice, @PathVariable String address,@PathVariable String maxEnergy) {
        Optional<Device> device = deviceService.findByAddressAndMaxHourEnergyConsumption(address,maxEnergy);
        Optional<Account> accountUser=accountService.findByUsername(dtoDevice.getUsername());
        Optional<RegularUser> regularUser=regularUserService.findById(accountUser.get().getUser().getUserId());
        if (device.isPresent()) {
            LOGGER.info("Device at address  " + device.get().getAddress() + " was found!");
            device.get().setAddress(dtoDevice.getAddress());
            device.get().setDescription(dtoDevice.getDescription());
            device.get().setRegularUser(regularUser.get());
            deviceService.save(device.get());
        }

        return ResponseEntity.status(HttpStatus.OK).body(device.orElse(new Device()));
    }
    @Transactional
    @DeleteMapping(path = "/deleteDevice/{address}")
    public @ResponseBody void deleteDevice(@PathVariable String address) {
        Optional<Device> device = deviceService.findByAddress(address);
        deviceService.deleteByDeviceId(device.get().getDeviceId());
    }

    @GetMapping(path = "/getAllDevices")
    public List<Device> getAllDevices() {
        Iterable<Device> allDevices=deviceService.findAllDevices();
        List<Device> allDeviceForSpecificUser=new ArrayList<>();
        for (Device currentDevice:allDevices) {
                allDeviceForSpecificUser.add(currentDevice);
        }

        return  allDeviceForSpecificUser;
    }
    @GetMapping(path = "/getAllRegularUsers")
    public List<RegularUser> getAllRegularUsers() {
        Iterable<RegularUser> allUsers=regularUserService.findAllUsers();
        List<RegularUser> listOfAllRegularUsers=new ArrayList<>();
        for (RegularUser currentUser:allUsers) {
            listOfAllRegularUsers.add(currentUser);
        }

        return  listOfAllRegularUsers;
    }
}
