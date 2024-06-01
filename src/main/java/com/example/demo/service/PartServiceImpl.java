package com.example.demo.service;

import com.example.demo.domain.Part;
import com.example.demo.repositories.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartServiceImpl implements PartService {
    private final PartRepository partRepository;

    @Autowired
    public PartServiceImpl(PartRepository partRepository) {
        this.partRepository = partRepository;
    }

    @Override
    public List<Part> findAll() {
        return (List<Part>) partRepository.findAll();
    }

    @Override
    public Part findById(int theId) {
        Long theIdl = (long) theId;
        Optional<Part> result = partRepository.findById(theIdl);

        Part thePart = null;

        if (result.isPresent()) {
            thePart = result.get();
        } else {
            throw new RuntimeException("Could not find the part id - " + theId);
        }

        return thePart;
    }

    @Override
    public void save(Part thePart) {
        partRepository.save(thePart);
    }

    @Override
    public void deleteById(int theId) {
        Long theIdl = (long) theId;
        partRepository.deleteById(theIdl);
    }

    @Override
    public List<Part> listAll(String keyword) {
        if (keyword != null) {
            return partRepository.search(keyword);
        }
        return (List<Part>) partRepository.findAll();
    }

    @Override
    public boolean decrementInventory(Long id) {
        Optional<Part> optionalPart = partRepository.findById(id);
        if (optionalPart.isPresent()) {
            Part part = optionalPart.get();
            if (part.getInv() > part.getMinInv()) {
                part.setInv(part.getInv() - 1);
                partRepository.save(part);
                return true;
            }
        }
        return false;
    }
}
