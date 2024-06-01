package com.example.demo.controllers;

import com.example.demo.domain.Part;
import com.example.demo.domain.Product;
import com.example.demo.service.PartService;
import com.example.demo.service.PartServiceImpl;
import com.example.demo.service.ProductService;
import com.example.demo.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AddProductController {

    @Autowired
    private ApplicationContext context;
    private final PartService partService;
    private Product product;

    public AddProductController(PartService partService) {
        this.partService = partService;
    }

    @GetMapping("/showFormAddProduct")
    public String showFormAddProduct(Model theModel) {
        theModel.addAttribute("parts", partService.findAll());
        product = new Product();
        theModel.addAttribute("product", product);

        List<Part> availParts = new ArrayList<>();
        for (Part p : partService.findAll()) {
            if (!product.getParts().contains(p)) availParts.add(p);
        }
        theModel.addAttribute("availparts", availParts);
        theModel.addAttribute("assparts", product.getParts());
        return "productForm";
    }

    @PostMapping("/showFormAddProduct")
    public String submitForm(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult, Model theModel) {
        if (bindingResult.hasErrors()) {
            ProductService productService = context.getBean(ProductServiceImpl.class);
            Product product2 = new Product();
            try {
                product2 = productService.findById((int) product.getId());
            } catch (Exception e) {
                System.out.println("Error Message " + e.getMessage());
            }
            theModel.addAttribute("product", product);
            theModel.addAttribute("parts", partService.findAll());
            List<Part> availParts = new ArrayList<>();
            for (Part p : partService.findAll()) {
                if (!product2.getParts().contains(p)) availParts.add(p);
            }
            theModel.addAttribute("availparts", availParts);
            theModel.addAttribute("assparts", product2.getParts());
            return "productForm";
        } else {
            ProductService repo = context.getBean(ProductServiceImpl.class);
            if (product.getId() != 0) {
                Product product2 = repo.findById((int) product.getId());
                PartService partService1 = context.getBean(PartServiceImpl.class);
                if (product.getInv() - product2.getInv() > 0) {
                    for (Part p : product2.getParts()) {
                        int inv = p.getInv();
                        p.setInv(inv - (product.getInv() - product2.getInv()));
                        partService1.save(p);
                    }
                }
            } else {
                product.setInv(0);
            }
            repo.save(product);
            return "confirmationaddproduct";
        }
    }

    @GetMapping("/showProductFormForUpdate")
    public String showProductFormForUpdate(@RequestParam("productID") int theId, Model theModel) {
        theModel.addAttribute("parts", partService.findAll());
        ProductService repo = context.getBean(ProductServiceImpl.class);
        Product theProduct = repo.findById(theId);
        product = theProduct;

        theModel.addAttribute("product", theProduct);
        theModel.addAttribute("assparts", theProduct.getParts());

        List<Part> availParts = new ArrayList<>();
        for (Part p : partService.findAll()) {
            if (!theProduct.getParts().contains(p)) availParts.add(p);
        }
        theModel.addAttribute("availparts", availParts);
        return "productForm";
    }

    @GetMapping("/deleteproduct")
    public String deleteProduct(@RequestParam("productID") int theId, Model theModel) {
        ProductService productService = context.getBean(ProductServiceImpl.class);
        Product product2 = productService.findById(theId);
        for (Part part : product2.getParts()) {
            part.getProducts().remove(product2);
            partService.save(part);
        }
        product2.getParts().removeAll(product2.getParts());
        productService.save(product2);
        productService.deleteById(theId);

        return "confirmationdeleteproduct";
    }

    @GetMapping("/associatepart")
    public String associatePart(@Valid @RequestParam("partID") int theID, Model theModel) {
        if (product == null || product.getName() == null) {
            return "saveproductscreen";
        } else {
            product.getParts().add(partService.findById(theID));
            partService.findById(theID).getProducts().add(product);
            ProductService productService = context.getBean(ProductServiceImpl.class);
            productService.save(product);
            partService.save(partService.findById(theID));

            theModel.addAttribute("product", product);
            theModel.addAttribute("assparts", product.getParts());

            List<Part> availParts = new ArrayList<>();
            for (Part p : partService.findAll()) {
                if (!product.getParts().contains(p)) availParts.add(p);
            }
            theModel.addAttribute("availparts", availParts);
            return "productForm";
        }
    }

    @GetMapping("/removepart")
    public String removePart(@RequestParam("partID") int theID, Model theModel) {
        product.getParts().remove(partService.findById(theID));
        partService.findById(theID).getProducts().remove(product);
        ProductService productService = context.getBean(ProductServiceImpl.class);
        productService.save(product);
        partService.save(partService.findById(theID));

        theModel.addAttribute("product", product);
        theModel.addAttribute("assparts", product.getParts());

        List<Part> availParts = new ArrayList<>();
        for (Part p : partService.findAll()) {
            if (!product.getParts().contains(p)) availParts.add(p);
        }
        theModel.addAttribute("availparts", availParts);
        return "productForm";
    }
}
