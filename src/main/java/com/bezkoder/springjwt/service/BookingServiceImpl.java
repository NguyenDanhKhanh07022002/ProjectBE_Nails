package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.config.MailConfig;
import com.bezkoder.springjwt.models.Booking;
import com.bezkoder.springjwt.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    MailConfig mailConfig;

    @Override
    public ResponseEntity<Object> createBooking(Booking booking) {
        String email = booking.getEmail().trim();
        String fullName = booking.getFullName();
        String phoneNumber = booking.getPhoneNumber();
        String date = booking.getDate();
        String time = booking.getTime();
        String bookingSelection = booking.getBookingService();
        String description = booking.getDescription();
        booking.setCreateAt(String.valueOf(System.currentTimeMillis()));
        booking.setDeleted(false);
        if (!isValidEmail(email)) {
            return ResponseEntity.badRequest().body(null);
        }
        Booking createBooking = bookingRepository.save(booking);
        String subject = "New Booking Details";
        String text = String.format("Booking Details:\n\nThank you for ordering our services!\nFull Name: %s\nBooking Service: %s\nPhone Number: %s\nDate: %s\nTime: %s\nDescription: %s",
                fullName, bookingSelection, phoneNumber, date, time, description);
        String configEmail = mailConfig.getRecipientEmail();
        String textConfig = String.format("Booking Details:\n\nFull Name: %s\nBooking Service: %s\nPhone Number: %s\nDate: %s\nTime: %s\nDescription: %s",
                fullName, bookingSelection, phoneNumber, date, time, description);
        sendEmail(email, subject, text);
        sendEmailConfig(configEmail, subject, textConfig);
        return ResponseEntity.ok(createBooking);
    }

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        javaMailSender.send(message);
    }

    public void sendEmailConfig(String to, String subject, String text) {
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
            booking.setBookingService(bookingDetails.getBookingService());
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
        List<Booking> bookings = bookingRepository.findAll();
        List<Booking> activeBookings = new ArrayList<>();
        for (Booking booking : bookings) {
            if (!booking.isDeleted()) {
                activeBookings.add(booking);
            }
        }
        return activeBookings;
    }

    @Override
    public List<Booking> findByPhoneNumber(String phoneNumber) {
        return (List<Booking>) bookingRepository.findByPhoneNumber(phoneNumber);
    }
}
