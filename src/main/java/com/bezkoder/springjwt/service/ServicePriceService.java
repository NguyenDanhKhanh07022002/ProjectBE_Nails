package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.models.ServicePrice;

import java.util.List;

public interface ServicePriceService {
    ServicePrice addServicePrice(ServicePrice servicePrice);
    ServicePrice updateServicePrice(Long id, ServicePrice updatedServicePrice);
    void deleteServicePrice(Long id);
    List<ServicePrice> getAllServicePrices();
    ServicePrice getServicePriceById(Long id);
}
