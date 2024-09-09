package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.ServicePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicePriceRepository extends JpaRepository<ServicePrice, Long> {
}
