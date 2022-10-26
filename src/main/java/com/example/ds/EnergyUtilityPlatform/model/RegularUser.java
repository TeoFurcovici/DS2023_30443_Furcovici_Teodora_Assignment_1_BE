package com.example.ds.EnergyUtilityPlatform.model;

import javax.persistence.*;
@Entity
public class RegularUser extends User {
    public RegularUser(Account userAccount) {
        super(userAccount);
    }

    public RegularUser() {

    }


}
