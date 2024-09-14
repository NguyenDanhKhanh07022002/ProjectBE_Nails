package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.config.MailConfig;
import com.bezkoder.springjwt.models.Booking;
import com.bezkoder.springjwt.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
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
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        String email = booking.getEmail().trim();
        String fullName = booking.getFullName();
        String phoneNumber = booking.getPhoneNumber();
        String date = booking.getDate();
        String time = booking.getTime();
        String bookingSelection = booking.getBookingService();
        String description = booking.getDescription();

        if (!isValidEmail(email)) {
            return ResponseEntity.badRequest().body(null);
        }

        Booking createBooking = bookingService.createBooking(booking);
        String subject = "New Booking Details";
        String text = String.format("Booking Details:\n\nFull Name: %s\nBooking Service: %s\nPhone Number: %s\nDate: %s\nTime: %s\nDescription: %s",
                fullName, bookingSelection, phoneNumber, date, time, description);
        String configEmail = mailConfig.getRecipientEmail();
        sendEmail(new String[] { email, configEmail }, subject, text);

        return ResponseEntity.ok(createBooking);
    }
    public void sendEmail(String[] to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        javaMailSender.send(message);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email != null && email.matches(emailRegex);
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

    @GetMapping("/getPhoneNumbet/{phoneNumber}")
    public List<Booking> findByPhonenumber(@PathVariable String phoneNumber) {
        return (List<Booking>) bookingService.findByPhoneNumber(phoneNumber);
    }
}
