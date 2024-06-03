package com.example.demo.service;
import com.example.demo.domain.Product;
import java.util.List;

public interface ProductService {
    List<Product> findAll();
    Product findById(int theId);
    void save(Product theProduct);
    void deleteById(int theId);
    List<Product> listAll(String keyword);
    boolean decrementInventory(Long id);
    boolean purchaseProduct(Long id);

}
