package com.example.ds.EnergyUtilityPlatform.service;

import com.example.ds.EnergyUtilityPlatform.model.Admin;
import com.example.ds.EnergyUtilityPlatform.model.Consumption;
import com.example.ds.EnergyUtilityPlatform.model.Device;
import com.example.ds.EnergyUtilityPlatform.repo.ConsumptionRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ConsumptionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumptionService.class);
    final String secretKey = "JHKLXABYZC!!!!";
    @Autowired
    private ConsumptionRepo consumptionRepo;

    public ConsumptionService(ConsumptionRepo consumptionRepo) {
        this.consumptionRepo = consumptionRepo;
    }

    public Consumption save(Consumption consumption) {
        LOGGER.info(" *** From ConsumptionService *** Consumption added at " + consumption.getDate() + " was saved!");
        return consumptionRepo.save(consumption);
    }
    public Optional<Consumption> findByDateAndHour(LocalDate date, String hour) {
        Optional<Consumption> consumption = consumptionRepo.findByDateAndHour(date,hour);
        if (consumption.isPresent()) {
            LOGGER.info("*** From ConsumptionService *** Consumption added at  " + date + " was found");
            return consumption;

        } else {
            LOGGER.error("*** From DeviceService *** Consumption added at  " + date + " was not found!");
            return null;
        }
    }
}
