package it.epicode.fs1024_eventi.service;

import it.epicode.fs1024_eventi.auth.AppUser;
import it.epicode.fs1024_eventi.auth.AppUserRepository;
import it.epicode.fs1024_eventi.auth.Role;
import it.epicode.fs1024_eventi.dto.EventRequest;
import it.epicode.fs1024_eventi.dto.EventResponse;
import it.epicode.fs1024_eventi.entity.Event;
import it.epicode.fs1024_eventi.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static it.epicode.fs1024_eventi.auth.Role.ROLE_ORGANIZER;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final AppUserRepository appUserRepository;

    private AppUser getCurrentUser(){
        String username = "XXXX";
        return appUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public EventResponse createEvent(EventRequest eventRequest ) {
        AppUser user = getCurrentUser();
        if (!user.getRoles().contains(ROLE_ORGANIZER))
            throw new RuntimeException("User is not an organizer");


        Event event = new Event();
        event.setTitle(eventRequest.getTitle());
        event.setDescription(eventRequest.getDescription());
        event.setDate(eventRequest.getDate());
        event.setLocation(eventRequest.getLocation());
        event.setTotalSeats(eventRequest.getTotalSeats());
        event.setAvailableSeats(eventRequest.getTotalSeats());
        event.setCreatedBy(user);

        Event savedEvent = eventRepository.save(event);
        return mapToResponse(savedEvent);

    }
    public List<EventResponse> getAllEvents() {
        return eventRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public EventResponse updateEvent(Long id, EventRequest eventRequest) {
        AppUser user = getCurrentUser();

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if(!event.getCreatedBy().getId().equals(user.getId()))
            throw new RuntimeException("You can't   update this event");

        event.setTitle(eventRequest.getTitle());
        event.setDescription(eventRequest.getDescription());
        event.setDate(eventRequest.getDate());
        event.setLocation(eventRequest.getLocation());
        event.setTotalSeats(eventRequest.getTotalSeats());
        event.setAvailableSeats(eventRequest.getTotalSeats());//reset posti

        return mapToResponse(eventRepository.save(event));
    }

    public void deleteEvent(Long id) {
        AppUser user = getCurrentUser();

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if(!event.getCreatedBy().getId().equals(user.getId()))
            throw new RuntimeException("You can't delete this event");

        eventRepository.delete(event);
    }

    private EventResponse mapToResponse(Event event) {
        return new EventResponse(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate(),
                event.getLocation(),
                event.getTotalSeats(),
                event.getAvailableSeats(),
                event.getCreatedBy().getUsername()
        );
    }


}
