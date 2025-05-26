package ba.unsa.etf.ppis.tim6.repository;

import ba.unsa.etf.ppis.tim6.model.FailoverEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FailoverEventRepository extends JpaRepository<FailoverEvent, Long> {
    List<FailoverEvent> findAllByOrderByTimestampDesc();
}