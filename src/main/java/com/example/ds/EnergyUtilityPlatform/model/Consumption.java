package com.example.ds.EnergyUtilityPlatform.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Consumption {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer consumptionId;

    @Column(name = "timestamp",nullable = false)
    private Timestamp timestamp;

    @Column(name = "energyConsumption",nullable = false)
    private String energyConsumption;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "deviceId")
    private Device device;

    public Integer getConsumptionId() {
        return consumptionId;
    }

    public void setConsumptionId(Integer consumptionId) {
        this.consumptionId = consumptionId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getEnergyConsumption() {
        return energyConsumption;
    }

    public void setEnergyConsumption(String energyConsumption) {
        this.energyConsumption = energyConsumption;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}
