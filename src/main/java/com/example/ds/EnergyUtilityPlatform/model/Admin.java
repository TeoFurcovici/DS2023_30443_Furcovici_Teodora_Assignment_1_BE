package com.example.ds.EnergyUtilityPlatform.model;

import javax.persistence.Entity;

@Entity
public class Admin extends User {
    public Admin(Account userAccount) {
        super(userAccount);
    }

    public Admin() {
        super();

    }


}