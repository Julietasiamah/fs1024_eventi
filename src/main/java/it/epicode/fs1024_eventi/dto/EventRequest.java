package it.epicode.fs1024_eventi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class EventRequest {
    private String title;
    private String description;
    private LocalDateTime date;
    private String location;
    private int totalSeats;
}
