package ba.unsa.etf.ppis.tim6.repository;

import ba.unsa.etf.ppis.tim6.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String createdByUsername);
}

