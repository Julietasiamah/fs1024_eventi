package it.epicode.fs1024_eventi.controller;

import it.epicode.fs1024_eventi.dto.EventRequest;
import it.epicode.fs1024_eventi.dto.EventResponse;
import it.epicode.fs1024_eventi.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping
    public ResponseEntity <EventResponse> create(@RequestBody EventRequest request){
        return ResponseEntity.ok(eventService.createEvent(request));
    }

    @GetMapping
    public ResponseEntity <EventResponse> getAll(){
        return ResponseEntity.ok((EventResponse) eventService.getAllEvents());
    }
    @PutMapping("/{id}")
    public ResponseEntity <EventResponse> update(@PathVariable Long id, @RequestBody EventRequest request){
        return ResponseEntity.ok(eventService.updateEvent(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <String> delete(@PathVariable Long id){
        eventService.deleteEvent(id);
        return ResponseEntity.ok("Event deleted successfully");
    }
}
