package ba.unsa.etf.ppis.tim6.repository;

import ba.unsa.etf.ppis.tim6.model.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {
    List<Incident> findByStatusInAndAssignedToIsNotNull(List<Incident.Status> statuses);
    List<Incident> findByStatusInAndAssignedToIsNull(List<Incident.Status> statuses);
    List<Incident> findByStatusIn(List<Incident.Status> statuses);
    int countByStatus(Incident.Status status);
}