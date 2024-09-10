package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.models.Booking;

import java.util.List;

public interface BookingService {
    Booking createBooking(Booking booking);
    Booking updateBooking(Long id, Booking booking);
    Booking getBookingById(Long id);
    void deleteBooking(Long id);
    List<Booking> getAllBookings();
}
