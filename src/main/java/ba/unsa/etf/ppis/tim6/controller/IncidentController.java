package ba.unsa.etf.ppis.tim6.controller;

import ba.unsa.etf.ppis.tim6.dto.CreateIncidentDTO;
import ba.unsa.etf.ppis.tim6.dto.IncidentDTO;
import ba.unsa.etf.ppis.tim6.dto.IncidentStatsDTO;
import ba.unsa.etf.ppis.tim6.dto.UpdateIncidentDTO;
import ba.unsa.etf.ppis.tim6.mapper.IncidentMapper;
import ba.unsa.etf.ppis.tim6.model.Incident;
import ba.unsa.etf.ppis.tim6.repository.IncidentRepository;
import ba.unsa.etf.ppis.tim6.repository.UserRepository;
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
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<IncidentDTO> createIncident(@RequestBody CreateIncidentDTO createIncidentDTO) {
        if (createIncidentDTO.getStatus() == null)
            createIncidentDTO.setStatus(String.valueOf(Incident.Status.OPEN));
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

    @GetMapping("/non-escalated-and-assigned")
    public ResponseEntity<List<IncidentDTO>> getNonEscalatedAndAssignedIncidents() {
        List<Incident.Status> nonEscalatedStatuses = List.of(Incident.Status.OPEN, Incident.Status.CLOSED);
        List<IncidentDTO> nonEscalatedAndAssignedIncidents = incidentRepository.findByStatusInAndAssignedToIsNotNull(nonEscalatedStatuses).stream()
                .map(incidentMapper::incidentToIncidentDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(nonEscalatedAndAssignedIncidents);
    }

    @GetMapping("/non-escalated-and-not-assigned")
    public ResponseEntity<List<IncidentDTO>> getNonEscalatedAndNotAssignedIncidents() {
        List<Incident.Status> nonEscalatedAndNotAssignedStatuses = List.of(Incident.Status.OPEN, Incident.Status.CLOSED);
        List<IncidentDTO> nonEscalated = incidentRepository.findByStatusInAndAssignedToIsNull(nonEscalatedAndNotAssignedStatuses).stream()
                .map(incidentMapper::incidentToIncidentDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(nonEscalated);
    }

    @GetMapping("/non-escalated")
    public ResponseEntity<List<IncidentDTO>> getNonEscalatedIncidents() {
        List<Incident.Status> nonEscalatedStatuses = List.of(Incident.Status.OPEN, Incident.Status.CLOSED);
        List<IncidentDTO> nonEscalated = incidentRepository.findByStatusIn(nonEscalatedStatuses).stream()
                .map(incidentMapper::incidentToIncidentDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(nonEscalated);
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
            if (updateIncidentDTO.getAction_taken() != null)
                existingIncident.setActionTaken(updateIncidentDTO.getAction_taken());

            if (updateIncidentDTO.getStatus() != null)
                existingIncident.setStatus(Incident.Status.valueOf(updateIncidentDTO.getStatus()));

            if(existingIncident.getDateResolved() != null)
                existingIncident.setDateResolved(updateIncidentDTO.getDate_resolved());

            if (updateIncidentDTO.getAssigned_to() != null) {
                userRepository.findById(updateIncidentDTO.getAssigned_to()).ifPresent(existingIncident::setAssignedTo);
            }
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

    @GetMapping("/stats")
    public ResponseEntity<IncidentStatsDTO> getIncidentStats() {
        int open = incidentRepository.countByStatus(Incident.Status.OPEN);
        int closed = incidentRepository.countByStatus(Incident.Status.CLOSED);
        int total = open + closed;

        IncidentStatsDTO stats = new IncidentStatsDTO(total, open, closed);
        return ResponseEntity.ok(stats);
    }

    @PutMapping("/{id}/escalate")
    public ResponseEntity<IncidentDTO> escalateIncident(@PathVariable Long id) {
        return incidentRepository.findById(id).map(incident -> {
            incident.setStatus(Incident.Status.ESCALATED);
            Incident updated = incidentRepository.save(incident);
            return ResponseEntity.ok(incidentMapper.incidentToIncidentDTO(updated));
        }).orElse(ResponseEntity.notFound().build());
    }

}
