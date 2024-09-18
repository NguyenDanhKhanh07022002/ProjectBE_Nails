package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.config.MailConfig;
import com.bezkoder.springjwt.models.Booking;
import com.bezkoder.springjwt.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    MailConfig mailConfig;

    @PostMapping("/create")
    public ResponseEntity<ResponseEntity<Object>> createBooking(@RequestBody Booking booking) {
        ResponseEntity<Object> newBooking = bookingService.createBooking(booking);
        return ResponseEntity.ok(newBooking);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @RequestBody Booking bookingDetails) {
        return ResponseEntity.ok(bookingService.updateBooking(id, bookingDetails));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @GetMapping("/getPhoneNumberOrEmail")
    public List<Booking> findByPhonenumber(@RequestParam(required = false) String phoneNumber,
                                           @RequestParam(required = false) String email) {
        return (List<Booking>) bookingService.findByPhoneNumber(phoneNumber.trim(), email.trim());
    }
}
