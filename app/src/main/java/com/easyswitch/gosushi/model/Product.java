package com.easyswitch.gosushi.model;

public class Product {

    private String name;
    private Integer mass;
    private String startDate;
    private String endDate;
    private String locationName;

    public Product() {

    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Product(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMass() {
        return mass;
    }

    public void setMass(Integer mass) {
        this.mass = mass;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
