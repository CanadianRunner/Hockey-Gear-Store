package com.example.demo.service;
import com.example.demo.domain.Part;
import com.example.demo.domain.Product;
import com.example.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final PartService partService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, PartService partService) {
        this.productRepository = productRepository;
        this.partService = partService;
    }

    @Override
    public List<Product> findAll() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public Product findById(int theId) {
        Long theIdl = (long) theId;
        Optional<Product> result = productRepository.findById(theIdl);

        Product theProduct = null;

        if (result.isPresent()) {
            theProduct = result.get();
        } else {
            throw new RuntimeException("Did not find product id - " + theId);
        }
        return theProduct;
    }

    @Override
    public void save(Product theProduct) {
        productRepository.save(theProduct);
    }

    @Override
    public void deleteById(int theId) {
        Long theIdl = (long) theId;
        productRepository.deleteById(theIdl);
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
            if (product.getInv() > 0) {
                product.setInv(product.getInv() - 1);
                productRepository.save(product);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean purchaseProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            if (product.getInv() > 0) {
                for (Part part : product.getParts()) {
                    if (part.getInv() <= part.getMinInv()) {
                        return false;
                    }
                }
                product.setInv(product.getInv() - 1);
                for (Part part : product.getParts()) {
                    part.setInv(part.getInv() - 1);
                    partService.save(part);
                }
                productRepository.save(product);
                return true;
            }
        }
        return false;
    }
}
