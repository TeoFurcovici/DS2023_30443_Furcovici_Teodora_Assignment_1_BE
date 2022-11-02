package com.example.ds.EnergyUtilityPlatform.repo;

import com.example.ds.EnergyUtilityPlatform.model.Consumption;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface ConsumptionRepo extends CrudRepository<Consumption,Integer> {
    Optional<Consumption> findByDateAndHour(LocalDate date, String hour);
}
