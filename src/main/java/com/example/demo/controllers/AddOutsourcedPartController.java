package com.example.demo.controllers;

import com.example.demo.domain.OutsourcedPart;
import com.example.demo.service.OutsourcedPartService;
import com.example.demo.service.OutsourcedPartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class AddOutsourcedPartController {
    @Autowired
    private ApplicationContext context;

    @GetMapping("/showFormAddOutPart")
    public String showFormAddOutsourcedPart(Model theModel) {
        OutsourcedPart outsourcedpart = new OutsourcedPart();
        theModel.addAttribute("outsourcedpart", outsourcedpart);
        return "OutsourcedPartForm";
    }

    @PostMapping("/showFormAddOutPart")
    public String submitForm(@Valid @ModelAttribute("outsourcedpart") OutsourcedPart part, BindingResult bindingResult, Model theModel) {
        theModel.addAttribute("outsourcedpart", part);
        if (bindingResult.hasErrors()) {
            return "OutsourcedPartForm";
        }
        if (part.getInv() > part.getMaxInv()) {
            theModel.addAttribute("errorMessage", "Inventory for part '" + part.getName() + "' exceeds the maximum limit of " + part.getMaxInv() + ".");
            return "highInventoryError";
        }
        OutsourcedPartService repo = context.getBean(OutsourcedPartServiceImpl.class);
        OutsourcedPart op = repo.findById((int) part.getId());
        if (op != null) part.setProducts(op.getProducts());
        repo.save(part);
        return "confirmationaddpart";
    }
}
