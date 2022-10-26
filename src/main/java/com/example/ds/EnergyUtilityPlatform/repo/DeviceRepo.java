package com.example.ds.EnergyUtilityPlatform.repo;


import com.example.ds.EnergyUtilityPlatform.model.Device;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DeviceRepo extends CrudRepository<Device,Integer> {
    Optional<Device> findByAddressAndDescription(String address,String description);
    Optional<Device> findByAddress(String address);
    Optional<Device> findById(Integer id);
    Optional<Device> findByAddressAndMaxHourEnergyConsumption(String address,String maxEnergy);

    void deleteByDeviceId(Integer deviceId);

}
