package it.epicode.fs1024_eventi.controller;

import it.epicode.fs1024_eventi.dto.BookingResponse;
import it.epicode.fs1024_eventi.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/{eventId}")
    public ResponseEntity <String> book(@PathVariable Long eventId) {
        return ResponseEntity.ok(bookingService.bookEvent(eventId));

    }
    @GetMapping
    public ResponseEntity <List<BookingResponse>>myBookings() {
        return ResponseEntity.ok(bookingService.getMyBookings());

    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity <String> cancelBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok(bookingService.cancelBooking(bookingId));

    }
}
