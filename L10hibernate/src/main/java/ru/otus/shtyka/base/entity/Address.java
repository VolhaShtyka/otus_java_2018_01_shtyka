package ru.otus.shtyka.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "address")
public class Address extends BaseEntity {

    @Column(name = "street")
    private String street;

    public Address() {
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                '}';
    }
}
