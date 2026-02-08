package ee.svekko.eventmgr.web.controller;

import ee.svekko.eventmgr.service.EventService;
import ee.svekko.eventmgr.web.dto.request.EventRegisterRequest;
import ee.svekko.eventmgr.web.dto.request.EventRequest;
import ee.svekko.eventmgr.web.dto.response.EventResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @GetMapping
    public List<EventResponse> getEvents() {
        return eventService.getEvents();
    }

    @PostMapping
    @Secured({"ROLE_ADMIN"})
    public void addEvent(@RequestBody @Valid EventRequest request, Principal principal) {
        eventService.addEvent(request, principal);
    }

    @PostMapping("/register")
    public void registerToEvent(@RequestBody @Valid EventRegisterRequest request) {
        eventService.registerToEvent(request);
    }
}
