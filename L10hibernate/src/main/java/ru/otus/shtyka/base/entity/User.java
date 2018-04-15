package ru.otus.shtyka.base.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address = new Address();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "id", cascade = CascadeType.ALL)
    private List<Phone> phones = new ArrayList<>();

    public User() {
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Address getAddress() {
        return address;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address=" + address +
                ", phones=" + phones +
                '}';
    }
}
