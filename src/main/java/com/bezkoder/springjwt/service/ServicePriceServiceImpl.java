package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.models.ServicePrice;
import com.bezkoder.springjwt.repository.ServicePriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicePriceServiceImpl implements ServicePriceService {
    @Autowired
    private ServicePriceRepository servicePriceRepository;
    @Override
    public ServicePrice addServicePrice(ServicePrice servicePrice) {
        return servicePriceRepository.save(servicePrice);
    }

    @Override
    public ServicePrice updateServicePrice(Long id, ServicePrice updatedServicePrice) {
        return null;
    }

    @Override
    public void deleteServicePrice(Long id) {

    }

    @Override
    public List<ServicePrice> getAllServicePrices() {
        return null;
    }

    @Override
    public ServicePrice getServicePriceById(Long id) {
        return null;
    }
}
