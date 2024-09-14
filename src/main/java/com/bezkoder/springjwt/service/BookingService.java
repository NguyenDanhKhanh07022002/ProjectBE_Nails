package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.models.Booking;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookingService {
    ResponseEntity<Object> createBooking(Booking booking);
    Booking updateBooking(Long id, Booking booking);
    Booking getBookingById(Long id);
    void deleteBooking(Long id);
    List<Booking> getAllBookings();

    List<Booking> findByPhoneNumber(String phoneNumber);
}
