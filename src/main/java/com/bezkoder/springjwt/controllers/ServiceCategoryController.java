package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.models.ServiceCategory;
import com.bezkoder.springjwt.models.ServicePrice;
import com.bezkoder.springjwt.service.ServiceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/serviceCategory")
public class ServiceCategoryController {

    @Autowired
    private ServiceCategoryService serviceCategoryService;
    @GetMapping("/getAll")
    public List<ServicePrice> getAllServiceCategory() {
        return null;
    }

    @PostMapping("/add_category")
    public ResponseEntity<?> addPriceCategory(@RequestBody ServiceCategory serviceCategory) {
        serviceCategoryService.addServiceCategory(serviceCategory);
        return ResponseEntity.ok().build();
    }
}
