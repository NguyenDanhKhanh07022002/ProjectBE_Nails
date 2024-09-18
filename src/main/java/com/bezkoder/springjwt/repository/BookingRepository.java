package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT b FROM Booking b WHERE b.phoneNumber = :phoneNumber  OR b.email = :email")
    List<Booking> findByPhoneNumber(@Param("phoneNumber") String phoneNumber, @Param("email") String email);
}
