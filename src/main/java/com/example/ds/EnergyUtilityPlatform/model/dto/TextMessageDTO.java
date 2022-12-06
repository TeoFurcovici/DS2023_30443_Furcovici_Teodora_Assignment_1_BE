package com.example.ds.EnergyUtilityPlatform.model.dto;

import javax.persistence.criteria.CriteriaBuilder;

public class TextMessageDTO {

    private String message;
    private String username;
    private Integer userId;
    private Integer deviceId;

    public TextMessageDTO(String message, String username, Integer userId, Integer deviceId) {
        this.message = message;
        this.username = username;
        this.userId = userId;
        this.deviceId = deviceId;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}