package it.epicode.fs1024_eventi.repository;


import it.epicode.fs1024_eventi.auth.AppUser;
import it.epicode.fs1024_eventi.entity.Booking;
import it.epicode.fs1024_eventi.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long>{
    List <Booking> findByUser (AppUser user);
    Optional <Booking> FindByUserAndEvent(AppUser user, Event event);
}
