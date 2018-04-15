package ru.otus.shtyka.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "phone")
public class Phone extends BaseEntity {

    @Column(name = "number")
    private String number;

    public Phone() {
    }

    public void setNumber(String number) {
        this.number = number;
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
        return "Phone{" +
                "number='" + number + '\'' +
                '}';
    }

}
