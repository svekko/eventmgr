package ee.svekko.eventmgr.domain.repository;

import ee.svekko.eventmgr.domain.model.EventEnrolment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EventEnrolmentRepository extends JpaRepository<EventEnrolment, UUID> {
    List<EventEnrolment> findByEventId(UUID eventId);
}
