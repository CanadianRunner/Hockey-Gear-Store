package com.example.demo.service;

import com.example.demo.domain.OutsourcedPart;

import java.util.List;

public interface OutsourcedPartService {
        List<OutsourcedPart> findAll();
        OutsourcedPart findById(int theId);
        void save(OutsourcedPart thePart);
        void deleteById(int theId);
}
