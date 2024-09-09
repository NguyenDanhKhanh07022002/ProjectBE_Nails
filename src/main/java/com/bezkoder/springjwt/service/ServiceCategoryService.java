package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.models.ServiceCategory;

import java.util.List;

public interface ServiceCategoryService {
    ServiceCategory addServiceCategory(ServiceCategory serviceCategory);
    ServiceCategory updateServiceCategory(Long id, ServiceCategory updatedServiceCategory);
    void deleteServiceCategory(Long id);
    List<ServiceCategory> getAllServiceCategories();
    ServiceCategory getServiceCategoryById(Long id);
    List<ServiceCategory> getServiceCategoriesByName(String name);
}
