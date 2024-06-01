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

    private PartService partService;

    private static Product product1;

    @GetMapping("/showFormAddProduct")
    public String showFormAddPart(Model theModel) {
        theModel.addAttribute("parts", partService.findAll());
        product1 = new Product();
        theModel.addAttribute("product", product1);

        List<Part> availParts = new ArrayList<>();
        for (Part p : partService.findAll()) {
            if (!product1.getParts().contains(p)) availParts.add(p);
        }
        theModel.addAttribute("availparts", availParts);
        theModel.addAttribute("assparts", product1.getParts());
        return "productForm";
    }

    @PostMapping("/showFormAddProduct")
    public String submitForm(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult, Model theModel) {
        theModel.addAttribute("product", product);

        if (bindingResult.hasErrors()) {
            theModel.addAttribute("parts", partService.findAll());
            List<Part> availParts = new ArrayList<>();
            for (Part p : partService.findAll()) {
                if (!product.getParts().contains(p)) availParts.add(p);
            }
            theModel.addAttribute("availparts", availParts);
            theModel.addAttribute("assparts", product.getParts());
            return "productForm";
        } else {
            ProductService repo = context.getBean(ProductServiceImpl.class);
            repo.save(product);
            return "confirmationaddproduct";
        }
    }

    @GetMapping("/showProductFormForUpdate")
    public String showProductFormForUpdate(@RequestParam("productID") int theId, Model theModel) {
        theModel.addAttribute("parts", partService.findAll());
        ProductService repo = context.getBean(ProductServiceImpl.class);
        Product theProduct = repo.findById(theId);
        product1 = theProduct;
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

    public AddProductController(PartService partService) {
        this.partService = partService;
    }

    @GetMapping("/associatepart")
    public String associatePart(@RequestParam("partID") int theID, Model theModel) {
        Part partToAdd = partService.findById(theID);
        if (!product1.getParts().contains(partToAdd)) {
            product1.getParts().add(partToAdd);
            partToAdd.getProducts().add(product1);
            ProductService productService = context.getBean(ProductServiceImpl.class);
            productService.save(product1);
            partService.save(partToAdd);
        } else {
            theModel.addAttribute("message", "Part is already associated with the product.");
        }
        theModel.addAttribute("product", product1);
        theModel.addAttribute("assparts", product1.getParts());
        List<Part> availParts = new ArrayList<>();
        for (Part p : partService.findAll()) {
            if (!product1.getParts().contains(p)) availParts.add(p);
        }
        theModel.addAttribute("availparts", availParts);
        return "productForm";
    }

    @GetMapping("/removepart")
    public String removePart(@RequestParam("partID") int theID, Model theModel) {
        Part partToRemove = partService.findById(theID);
        product1.getParts().remove(partToRemove);
        partToRemove.getProducts().remove(product1);
        ProductService productService = context.getBean(ProductServiceImpl.class);
        productService.save(product1);
        partService.save(partToRemove);
        theModel.addAttribute("product", product1);
        theModel.addAttribute("assparts", product1.getParts());
        List<Part> availParts = new ArrayList<>();
        for (Part p : partService.findAll()) {
            if (!product1.getParts().contains(p)) availParts.add(p);
        }
        theModel.addAttribute("availparts", availParts);
        return "productForm";
    }
}
