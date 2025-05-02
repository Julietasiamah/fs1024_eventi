package it.epicode.fs1024_eventi.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingResponse {
    private Long bookingId;
    private Long eventId;
    private String eventName;
    private LocalDateTime eventDate;
    private String eventLocation;

    public BookingResponse(Long id, Long id1, String title, LocalDateTime date, String location, Object o) {
    }
}
