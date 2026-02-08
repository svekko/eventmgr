package ee.svekko.eventmgr.domain.repository;

import ee.svekko.eventmgr.domain.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
    List<Event> findAllByOrderByEventDatetimeAsc();

    @Query(
        value = """
            SELECT *
            FROM event
            WHERE title ILIKE :title
            """,
        nativeQuery = true
    )
    Optional<Event> findByTitle(String title);
}
