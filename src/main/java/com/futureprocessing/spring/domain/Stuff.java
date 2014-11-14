package com.futureprocessing.spring.domain;

public class Stuff {

    private String description;
    private DomainUser owner;
    private String details;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DomainUser getOwner() {
        return owner;
    }

    public void setOwner(DomainUser owner) {
        this.owner = owner;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
