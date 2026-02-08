package ee.svekko.eventmgr.service;

import ee.svekko.eventmgr.domain.model.Event;
import ee.svekko.eventmgr.domain.model.EventEnrolment;
import ee.svekko.eventmgr.domain.repository.EventEnrolmentRepository;
import ee.svekko.eventmgr.domain.repository.EventRepository;
import ee.svekko.eventmgr.error.ApiError;
import ee.svekko.eventmgr.exception.ApiException;
import ee.svekko.eventmgr.mapper.EventMapper;
import ee.svekko.eventmgr.web.dto.request.EventRegisterRequest;
import ee.svekko.eventmgr.web.dto.request.EventRequest;
import ee.svekko.eventmgr.web.dto.response.EventResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    private final EventEnrolmentRepository eventEnrolmentRepository;

    private final EventMapper eventMapper;

    private final EstIdService estIdService;

    public List<EventResponse> getEvents() {
        return eventRepository.findAllByOrderByEventDatetimeAsc()
            .stream()
            .map(eventMapper::toEventResponse)
            .toList();
    }

    @Transactional
    public void addEvent(EventRequest request, Principal principal) {
        if (request.getEventDatetime().isBefore(OffsetDateTime.now())) {
            throw ApiException.badRequest(ApiError.EVENT_DATE_ALREADY_IN_PAST);
        }

        if (eventRepository.findByTitle(request.getTitle()).isPresent()) {
            throw ApiException.badRequest(ApiError.EVENT_WITH_SUCH_TITLE_ALREADY_EXISTS);
        }

        eventRepository.save(Event.builder()
            .title(request.getTitle())
            .maxEnrolments(request.getMaxEnrolments())
            .eventDatetime(request.getEventDatetime())
            .createdAt(OffsetDateTime.now())
            .createdBy(principal.getName())
            .build());
    }

    @Transactional
    public void registerToEvent(EventRegisterRequest request) {
        if (!estIdService.validate(request.getIdCode())) {
            throw ApiException.badRequest(ApiError.INVALID_ID_CODE);
        }

        Event event = eventRepository.findById(request.getEventId())
            .orElseThrow(() -> ApiException.notFound(ApiError.EVENT_NOT_FOUND));

        if (event.getEventDatetime().isBefore(OffsetDateTime.now())) {
            throw ApiException.badRequest(ApiError.EVENT_DATE_ALREADY_IN_PAST);
        }

        List<EventEnrolment> enrolments = eventEnrolmentRepository.findByEventId(event.getId());
        if (enrolments.size() >= event.getMaxEnrolments()) {
            throw ApiException.badRequest(ApiError.EVENT_NO_FREE_SPOTS_LEFT);
        }

        if (enrolments.stream().anyMatch(e -> e.getIdCode().equals(request.getIdCode()))) {
            throw ApiException.badRequest(ApiError.EVENT_PERSON_ALREADY_ENROLLED);
        }

        eventEnrolmentRepository.save(EventEnrolment.builder()
            .event(event)
            .idCode(request.getIdCode())
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .createdAt(OffsetDateTime.now())
            .build());
    }
}
