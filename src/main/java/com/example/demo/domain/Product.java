package com.example.demo.domain;
import com.example.demo.validators.ValidProductInventory;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@ValidProductInventory
@Table(name = "Products")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @Min(value = 0, message = "Price value must be positive")
    private double price;

    @Min(value = 0, message = "Inventory value must be positive")
    private int inv;

    @NotNull(message = "Minimum inventory must not be null")
    @Min(value = 0, message = "Minimum inventory must be positive")
    private int minInv;

    @NotNull(message = "Maximum inventory must not be null")
    @Min(value = 0, message = "Maximum inventory must be positive")
    private int maxInv;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "product_part", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "part_id"))
    private Set<Part> parts = new HashSet<>();

    // Constructors, getters, and setters
    public Product() {
    }

    public Product(String name, double price, int inv, int minInv, int maxInv) {
        this.name = name;
        this.price = price;
        this.inv = inv;
        this.minInv = minInv;
        this.maxInv = maxInv;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getInv() {
        return inv;
    }

    public void setInv(int inv) {
        this.inv = inv;
    }

    public int getMinInv() {
        return minInv;
    }

    public void setMinInv(int minInv) {
        this.minInv = minInv;
    }

    public int getMaxInv() {
        return maxInv;
    }

    public void setMaxInv(int maxInv) {
        this.maxInv = maxInv;
    }

    public Set<Part> getParts() {
        return parts;
    }

    public void setParts(Set<Part> parts) {
        this.parts = parts;
    }
}
