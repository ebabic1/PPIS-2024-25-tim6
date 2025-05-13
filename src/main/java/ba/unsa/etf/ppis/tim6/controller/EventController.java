package ba.unsa.etf.ppis.tim6.controller;

import ba.unsa.etf.ppis.tim6.model.Event;
import ba.unsa.etf.ppis.tim6.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @GetMapping
    public List<Event> getAllIncidents() {
        return eventRepository.findAll();
    }
}