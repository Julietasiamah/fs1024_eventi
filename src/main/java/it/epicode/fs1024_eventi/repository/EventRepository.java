package it.epicode.fs1024_eventi.repository;

import it.epicode.fs1024_eventi.auth.AppUser;
import it.epicode.fs1024_eventi.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

//import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long>{
    //List <Event> findByCreatedBy(AppUser createdBy);

}
