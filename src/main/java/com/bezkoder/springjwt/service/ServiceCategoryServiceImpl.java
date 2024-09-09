package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.models.ServiceCategory;
import com.bezkoder.springjwt.repository.ServiceCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceCategoryServiceImpl implements ServiceCategoryService {

    @Autowired
    private ServiceCategoryRepository serviceCategoryRepository;
    @Override
    public ServiceCategory addServiceCategory(ServiceCategory serviceCategory) {
        return serviceCategoryRepository.save(serviceCategory);
    }

    @Override
    public ServiceCategory updateServiceCategory(Long id, ServiceCategory updatedServiceCategory) {
        return serviceCategoryRepository.findById(id)
                .map(existingServiceCategory -> {
            existingServiceCategory.setCategoryName(updatedServiceCategory.getCategoryName());
            return serviceCategoryRepository.save(existingServiceCategory);
        }).orElseThrow(() -> new RuntimeException("ServiceCategory not found with id " + id));
    }

    @Override
    public void deleteServiceCategory(Long id) {

    }

    @Override
    public List<ServiceCategory> getAllServiceCategories() {
        return null;
    }

    @Override
    public ServiceCategory getServiceCategoryById(Long id) {
        return null;
    }

    @Override
    public List<ServiceCategory> getServiceCategoriesByName(String name) {
        return null;
    }
}
