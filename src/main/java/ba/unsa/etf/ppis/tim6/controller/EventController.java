package ba.unsa.etf.ppis.tim6.controller;

import ba.unsa.etf.ppis.tim6.dto.EventDTO;
import ba.unsa.etf.ppis.tim6.mapper.EventMapper;
import ba.unsa.etf.ppis.tim6.model.Event;
import ba.unsa.etf.ppis.tim6.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    // Create a new Event
    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@RequestBody EventDTO eventDTO) {
        Event event = eventMapper.eventDTOToEvent(eventDTO);
        Event savedEvent = eventRepository.save(event);
        return ResponseEntity.ok(eventMapper.eventToEventDTO(savedEvent));
    }

    // Retrieve all Events
    @GetMapping
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        List<EventDTO> events = eventRepository.findAll().stream()
                .map(eventMapper::eventToEventDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(events);
    }

    // Retrieve a specific Event by ID
    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long id) {
        Optional<Event> event = eventRepository.findById(id);
        return event.map(value -> ResponseEntity.ok(eventMapper.eventToEventDTO(value)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Update an existing Event by ID
    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Long id, @RequestBody EventDTO eventDTO) {
        return eventRepository.findById(id).map(existingEvent -> {
            existingEvent.setDescription(eventDTO.getDescription());
            existingEvent.setSeverityLevel(eventDTO.getSeverity_level());
            existingEvent.setStatus(eventDTO.getStatus());
            Event updatedEvent = eventRepository.save(existingEvent);
            return ResponseEntity.ok(eventMapper.eventToEventDTO(updatedEvent));
        }).orElse(ResponseEntity.notFound().build());
    }

    // Delete an Event by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
