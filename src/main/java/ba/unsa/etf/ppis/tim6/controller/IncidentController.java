package ba.unsa.etf.ppis.tim6.controller;

import ba.unsa.etf.ppis.tim6.dto.CreateIncidentDTO;
import ba.unsa.etf.ppis.tim6.dto.IncidentDTO;
import ba.unsa.etf.ppis.tim6.dto.UpdateIncidentDTO;
import ba.unsa.etf.ppis.tim6.mapper.IncidentMapper;
import ba.unsa.etf.ppis.tim6.model.Incident;
import ba.unsa.etf.ppis.tim6.repository.IncidentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/incidents")
@RequiredArgsConstructor
public class IncidentController {

    private final IncidentRepository incidentRepository;
    private final IncidentMapper incidentMapper;

    @PostMapping
    public ResponseEntity<IncidentDTO> createIncident(@RequestBody CreateIncidentDTO createIncidentDTO) {
        Incident incident = incidentMapper.createIncidentDTOToIncident(createIncidentDTO);
        Incident savedIncident = incidentRepository.save(incident);
        return ResponseEntity.ok(incidentMapper.incidentToIncidentDTO(savedIncident));
    }

    @GetMapping
    public ResponseEntity<List<IncidentDTO>> getAllIncidents() {
        List<IncidentDTO> incidents = incidentRepository.findAll().stream()
                .map(incidentMapper::incidentToIncidentDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(incidents);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncidentDTO> getIncidentById(@PathVariable Long id) {
        Optional<Incident> incident = incidentRepository.findById(id);
        return incident.map(value -> ResponseEntity.ok(incidentMapper.incidentToIncidentDTO(value)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<IncidentDTO> updateIncident(@PathVariable Long id, @RequestBody UpdateIncidentDTO updateIncidentDTO) {
        return incidentRepository.findById(id).map(existingIncident -> {
            existingIncident.setActionTaken(updateIncidentDTO.getAction_taken());
            Incident updatedIncident = incidentRepository.save(existingIncident);
            return ResponseEntity.ok(incidentMapper.incidentToIncidentDTO(updatedIncident));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncident(@PathVariable Long id) {
        if (incidentRepository.existsById(id)) {
            incidentRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
