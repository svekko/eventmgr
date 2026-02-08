package ee.svekko.eventmgr.mapper;

import ee.svekko.eventmgr.domain.model.Event;
import ee.svekko.eventmgr.domain.repository.EventEnrolmentRepository;
import ee.svekko.eventmgr.web.dto.response.EventResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventMapper {
    private final EventEnrolmentRepository eventEnrolmentRepository;

    public EventResponse toEventResponse(Event event) {
        return EventResponse.builder()
            .id(event.getId())
            .title(event.getTitle())
            .maxEnrolments(event.getMaxEnrolments())
            .eventDatetime(event.getEventDatetime())
            .noOfEnrolments(getNoOfEnrolments(event))
            .build();
    }

    private int getNoOfEnrolments(Event event) {
        return eventEnrolmentRepository.findByEventId(event.getId()).size();
    }
}
