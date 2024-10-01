package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.config.MailConfig;
import com.bezkoder.springjwt.config.SentMail;
import com.bezkoder.springjwt.models.Booking;
import com.bezkoder.springjwt.models.MessageNotify;
import com.bezkoder.springjwt.repository.BookingRepository;
import com.bezkoder.springjwt.socket.SocketModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    private MailConfig mailConfig;

    @Autowired
    private SentMail sentMail;
    private
    SocketModule socketModule;

    private static final String MANICURE = "Manicure";
    private static final String PEDICURE = "Pedicure";
    private static final String MANICURE_PEDICURE = "Manicure + Pedicure";
    private static final String PRODLUZOVANI_RAS = "Prodluzování Ras";
    private static final String MASSAGE = "Massage";
    private static final String COSMETICS = "Cosmetics";

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
        switch (bookingSelection) {
            case "1":
                bookingSelection = MANICURE;
                break;
            case "2":
                bookingSelection = PEDICURE;
                break;
            case "3":
                bookingSelection = MANICURE_PEDICURE;
                break;
            case "4":
                bookingSelection = PRODLUZOVANI_RAS;
                break;
            case "5":
                bookingSelection = MASSAGE;
                break;
            default:
                bookingSelection = COSMETICS;
                break;
        }
        Booking createBooking = bookingRepository.save(booking);
        String subject = "New Booking Details";
        String text = String.format(
                "Booking Details:\n\nThank you for ordering our services!\nFull Name: %s\nBooking Service: %s\nPhone Number: %s\nDate: %s\nTime: %s\nDescription: %s",
                fullName, bookingSelection, phoneNumber, date, time, description);
        sentMail.sendEmail(email, subject, text);
        String configEmail = mailConfig.getRecipientEmail();
        String textConfig = String.format(
                "Booking Details:\n\nFull Name: %s\nBooking Service: %s\nPhone Number: %s\nDate: %s\nTime: %s\nDescription: %s",
                fullName, bookingSelection, phoneNumber, date, time, description);
        sentMail.sendEmail(configEmail, subject, textConfig);

        MessageNotify messageNotify = new MessageNotify();
        messageNotify.setContent(text);
        socketModule.broadcastMessage("3", messageNotify);
        return ResponseEntity.ok(createBooking);
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
        Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isPresent()) {
            Booking existingBooking = booking.get();
            existingBooking.setDeleted(true);
            bookingRepository.save(existingBooking);
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
    public List<Booking> findByPhoneNumber(String phoneNumber, String email) {
        return (List<Booking>) bookingRepository.findByPhoneNumber(phoneNumber, email);
    }
}
