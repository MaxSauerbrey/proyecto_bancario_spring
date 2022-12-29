package com.example.proyectoBancario3.entities;

//import jakarta.persistence.*;

import javax.persistence.*;

@Entity
@Table(name = "usuario")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String cardNumber;
    private Double savingAccount;


    public User() {
    }

    public User(Long id, String username, String password, String cardNumber, Double savingAccount) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.cardNumber = cardNumber;
        this.savingAccount = savingAccount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Double getSavingAccount() {
        return savingAccount;
    }

    public void setSavingAccount(Double savingAccount) {
        this.savingAccount = savingAccount;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", savingAccount=" + savingAccount +
                '}';
    }
}

