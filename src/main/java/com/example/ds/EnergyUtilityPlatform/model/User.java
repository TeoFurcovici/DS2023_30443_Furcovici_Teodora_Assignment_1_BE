package com.example.ds.EnergyUtilityPlatform.model;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;
    @OneToOne(cascade=CascadeType.PERSIST)
    private Account userAccount;

    public User() {

    }

    public User(Account userAccount) {
        this.userAccount = userAccount;
    }


    public Integer getUserId() {
        return userId;
    }

    public Account getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(Account userAccount) {
        this.userAccount = userAccount;
    }

}
