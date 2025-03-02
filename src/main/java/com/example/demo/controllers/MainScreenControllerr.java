package com.example.demo.controllers;

import com.example.demo.domain.Part;
import com.example.demo.domain.Product;
import com.example.demo.service.PartService;
import com.example.demo.service.ProductService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MainScreenControllerr {

    private final PartService partService;
    private final ProductService productService;

    public MainScreenControllerr(PartService partService, ProductService productService) {
        this.partService = partService;
        this.productService = productService;
    }

    @GetMapping("/mainscreen")
    public String listPartsAndProducts(Model theModel, @Param("partkeyword") String partkeyword, @Param("productkeyword") String productkeyword) {
        List<Part> partList = partService.listAll(partkeyword);
        theModel.addAttribute("parts", partList);
        theModel.addAttribute("partkeyword", partkeyword);

        List<Product> productList = productService.listAll(productkeyword);
        theModel.addAttribute("products", productList);
        theModel.addAttribute("productkeyword", productkeyword);
        return "mainscreen";
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "about";
    }

    @PostMapping("/buyProduct")
    public String buyProduct(@RequestParam("productID") Long productID, Model model) {
        boolean success = productService.purchaseProduct(productID);
        if (success) {
            model.addAttribute("message", "Purchase successful!");
            return "buyProductSuccess";
        } else {
            model.addAttribute("message", "Purchase failed: Not enough inventory.");
            return "buyProductError";
        }
    }

    @PostMapping("/buyPart")
    public String buyPart(@RequestParam("partID") Long partID, Model model) {
        boolean success = partService.decrementInventory(partID);
        if (success) {
            model.addAttribute("message", "Purchase successful!");
            return "buyPartSuccess";
        } else {
            model.addAttribute("message", "Purchase failed: Not enough inventory.");
            return "buyPartError";
        }
    }
}
