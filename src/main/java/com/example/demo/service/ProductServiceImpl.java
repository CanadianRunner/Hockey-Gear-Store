package com.example.demo.service;
import com.example.demo.domain.Product;
import com.example.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAll() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public Product findById(int theId) {
        Optional<Product> result = productRepository.findById((long) theId);

        Product theProduct = null;

        if (result.isPresent()) {
            theProduct = result.get();
        } else {
            throw new RuntimeException("Sorry could not find the product id - " + theId);
        }

        return theProduct;
    }

    @Override
    public void save(Product theProduct) {
        if (theProduct.getInv() < theProduct.getMinInv() || theProduct.getInv() > theProduct.getMaxInv()) {
            throw new RuntimeException("Error! Inventory must be set between the min and max values.");
        }
        productRepository.save(theProduct);
    }

    @Override
    public void deleteById(int theId) {
        productRepository.deleteById((long) theId);
    }

    @Override
    public List<Product> listAll(String keyword) {
        if (keyword != null) {
            return productRepository.search(keyword);
        }
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public boolean decrementInventory(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            if (product.getInv() > product.getMinInv()) {
                product.setInv(product.getInv() - 1);
                productRepository.save(product);
                return true;
            }
        }
        return false;
    }
}
