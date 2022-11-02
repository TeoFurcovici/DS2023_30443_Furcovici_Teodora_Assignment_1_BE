package com.example.ds.EnergyUtilityPlatform.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Consumption {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer consumptionId;

    @Column(name = "date",nullable = false)
    private LocalDate date;

    @Column(name = "hour",nullable = false)
    private String hour;

    @Column(name = "energyConsumption",nullable = false)
    private String energyConsumption;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "deviceId")
    private Device device;

    public Consumption() {
    }

    public Consumption(LocalDate date, String hour, String energyConsumption, Device device) {
        this.date = date;
        this.hour = hour;
        this.energyConsumption = energyConsumption;
        this.device = device;
    }

    public Integer getConsumptionId() {
        return consumptionId;
    }

    public void setConsumptionId(Integer consumptionId) {
        this.consumptionId = consumptionId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
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
