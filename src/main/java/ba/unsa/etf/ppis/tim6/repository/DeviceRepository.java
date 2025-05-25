package ba.unsa.etf.ppis.tim6.repository;

import ba.unsa.etf.ppis.tim6.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {
}
