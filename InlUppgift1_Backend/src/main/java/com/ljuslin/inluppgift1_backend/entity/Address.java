package com.ljuslin.inluppgift1_backend.entity;

import jakarta.persistence.*;
/*Address ska innehålla id, street, postalCode, city. En medlem kan endast ha en adress, men en adress
kan kopplas till flera medlemmar*/
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "street", length = 100, nullable = false)
    private String street;

    @Column(name = "postal_code", length = 10, nullable = false)
    private String postalCode;

    @Column(name = "city", length = 50, nullable = false)
    private String city;

    protected Address() {
    }

    public Address(String street, String postalCode, String city) {
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
    }

    public Address(Long id, String street, String postalCode, String city) {
        this.id = id;
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
