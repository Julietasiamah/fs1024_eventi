package it.epicode.fs1024_eventi.service;

import it.epicode.fs1024_eventi.auth.AppUser;
import it.epicode.fs1024_eventi.auth.AppUserRepository;
import it.epicode.fs1024_eventi.dto.BookingResponse;
import it.epicode.fs1024_eventi.entity.Booking;
import it.epicode.fs1024_eventi.entity.Event;
import it.epicode.fs1024_eventi.repository.BookingRepository;
import it.epicode.fs1024_eventi.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final EventRepository eventRepository;
    private final AppUserRepository appUserRepository;

    private AppUser getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        return appUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Prenotazione evento
    public String bookEvent(Long eventId) {
        AppUser user = getCurrentUser();
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
            //se le postazioni sono esaurite
        if (event.getAvailableSeats() <= 0)
            throw new RuntimeException("Event is full");

        //se l'utente ha già prenotato l'evento
        boolean alreadyBooked = bookingRepository.existsByUserAndEvent(user, event);
        if (alreadyBooked)
            throw new RuntimeException("Event already booked");
        //creo la prenotazione
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setEvent(event);

        bookingRepository.save(booking);
        event.setAvailableSeats(event.getAvailableSeats() - 1);
        eventRepository.save(event);

        return "Booking successful";
    }

    // Ottieni le prenotazioni dell'utente
    public List<BookingResponse> getMyBookings() {
        AppUser user = getCurrentUser();
        return bookingRepository.findByUser(user).stream()
                .map(booking -> new BookingResponse(
                        booking.getId(),
                        booking.getEvent().getId(),
                        booking.getEvent().getTitle(),
                        booking.getEvent().getDate(),
                        booking.getEvent().getLocation()
                ))
                .toList();
    }

    // Annulla una prenotazione
    public String cancelBooking(Long bookingId) {
        AppUser user = getCurrentUser();
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        //se l'utente non ha prenotato l'evento
        if (!booking.getUser().getId().equals(user.getId()))
            throw new RuntimeException("You can't cancel this booking");
        //annullo la prenotazione
        Event event = booking.getEvent();
        event.setAvailableSeats(event.getAvailableSeats() + 1);
        eventRepository.save(event);
        //elimino la prenotazione
        bookingRepository.delete(booking);
        return "Booking canceled";
    }
}