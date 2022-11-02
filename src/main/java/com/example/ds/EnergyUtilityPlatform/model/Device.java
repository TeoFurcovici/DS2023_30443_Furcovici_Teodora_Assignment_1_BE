package com.example.ds.EnergyUtilityPlatform.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer deviceId;

    @Column(name="description",nullable = false)
    private String description;

    @Column(name="address",nullable = false)
    private String address;

    @Column(name="maxHourEnergyConsumption",nullable = false)
    private String maxHourEnergyConsumption;

    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="userId")
    private  RegularUser regularUser;

    public Device(String description, String address, String maxHourEnergyConsumption) {
        this.description = description;
        this.address = address;
        this.maxHourEnergyConsumption = maxHourEnergyConsumption;
    }

    public Device() {

    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMaxHourEnergyConsumption() {
        return maxHourEnergyConsumption;
    }

    public void setMaxHourEnergyConsumption(String maxHourEnergyConsumption) {
        this.maxHourEnergyConsumption = maxHourEnergyConsumption;
    }

    public RegularUser getRegularUser() {
        return regularUser;
    }

    public void setRegularUser(RegularUser regularUser) {
        this.regularUser = regularUser;
    }

}
