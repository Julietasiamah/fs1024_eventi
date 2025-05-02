package it.epicode.fs1024_eventi.entity;

import io.swagger.v3.oas.annotations.info.Info;
import it.epicode.fs1024_eventi.auth.AppUser;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String title;
    private String description;
    private LocalDateTime date;
    private String location;
    private int totalSeats;
    private int availableSeats;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private AppUser createdBy;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List <Booking> bookings;


}
