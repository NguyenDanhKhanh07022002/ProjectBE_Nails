package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.models.Booking;
import com.bezkoder.springjwt.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Override
    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public Booking updateBooking(Long id, Booking bookingDetails) {
        Optional<Booking> bookingOptional = bookingRepository.findById(id);
        if (bookingOptional.isPresent()) {
            Booking booking = bookingOptional.get();
            booking.setDate(bookingDetails.getDate());
            booking.setTime(bookingDetails.getTime());
            booking.setFullName(bookingDetails.getFullName());
            booking.setPhoneNumber(bookingDetails.getPhoneNumber());
            booking.setEmail(bookingDetails.getEmail());
            booking.setDescription(bookingDetails.getDescription());
            return bookingRepository.save(booking);
        } else {
            throw new RuntimeException("Booking not found with id: " + id);
        }
    }

    @Override
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
    }

    @Override
    public void deleteBooking(Long id) {
        if (bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id);
        } else {
            throw new RuntimeException("Booking not found with id: " + id);
        }
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public List<Booking> findByPhoneNumber(String phoneNumber) {
        return (List<Booking>) bookingRepository.findByPhoneNumber(phoneNumber);
    }
}
