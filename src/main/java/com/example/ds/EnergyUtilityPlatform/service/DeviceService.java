package com.example.ds.EnergyUtilityPlatform.service;

import com.example.ds.EnergyUtilityPlatform.model.Device;
import com.example.ds.EnergyUtilityPlatform.repo.DeviceRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class DeviceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);
    @Autowired
    private DeviceRepo deviceRepo;

    public DeviceService(DeviceRepo deviceRepo) {
        this.deviceRepo = deviceRepo;
    }

    public Device save(Device device) {
        LOGGER.info(" *** From DeviceService *** Device at address  " + device.getAddress() + " was saved!");
        return deviceRepo.save(device);
    }

    public Optional<Device> findByAddressAndDescription(String address,String description) {
        Optional<Device> device = deviceRepo.findByAddressAndDescription(address,description);
        if (device.isPresent()) {
            LOGGER.info("*** From DeviceService *** Device at address  " + address + " was found");
            return device;

        } else {
            LOGGER.error("*** From DeviceService *** Device at address  " + device + " was not found!");
            return null;
        }
    }
    public Optional<Device> findByAddress(String address) {
        Optional<Device> device = deviceRepo.findByAddress(address);
        if (device.isPresent()) {
            LOGGER.info("*** From DeviceService *** Device at address  " + address + " was found");
            return device;

        } else {
            LOGGER.error("*** From DeviceService *** Device at address  " + address + " was not found!");
            return null;
        }
    }

    public Optional<Device> findByAddressAndMaxHourEnergyConsumption(String address, String maxEnergy) {
        Optional<Device> device = deviceRepo.findByAddressAndMaxHourEnergyConsumption(address,maxEnergy);
        if (device.isPresent()) {
            LOGGER.info("*** From DeviceService *** Device at address  " + address + " was found");
            return device;

        } else {
            LOGGER.error("*** From DeviceService *** Device at address  " + address + " was not found!");
            return null;
        }
    }

    @Transactional
    public void deleteByDeviceId(Integer deviceId) {
        LOGGER.info(" *** From DeviceService *** Device " + deviceId + " was deleted!");
        deviceRepo.deleteByDeviceId(deviceId);
    }

    public Optional<Device> findById(Integer id) {
        Optional<Device> device = deviceRepo.findById(id);
        if (device.isPresent()) {
            LOGGER.info("*** From DeviceService *** Device at address  " + device.get().getAddress() + " was found");
            return device;

        } else {
            LOGGER.error("*** From DeviceService *** Device at address  " + device.get().getAddress() + " was not found!");
            return null;
        }
    }
    public Iterable<Device> findAllDevices()
    {
        return deviceRepo.findAll();
    }
}
