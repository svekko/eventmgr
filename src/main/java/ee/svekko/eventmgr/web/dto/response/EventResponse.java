package ee.svekko.eventmgr.web.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
public class EventResponse {
    private UUID id;

    private String title;

    private OffsetDateTime eventDatetime;

    private int maxEnrolments;

    private int noOfEnrolments;
}
