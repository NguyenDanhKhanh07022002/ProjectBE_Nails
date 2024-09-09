package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.models.ServiceCategory;
import com.bezkoder.springjwt.models.ServicePrice;
import com.bezkoder.springjwt.service.ServicePriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicePrice")
public class ServicePriceController {

    @Autowired
    private ServicePriceService servicePriceService;
    @GetMapping("/getAll")
    public List<ServicePrice> getAllServicePrice() {
        return null;
    }

    @PostMapping("/add_category")
    public ResponseEntity<?> addPriceCategory(@RequestBody ServicePrice serviceCategory) {
        servicePriceService.addServicePrice(serviceCategory);
        return ResponseEntity.ok().build();
    }
}
