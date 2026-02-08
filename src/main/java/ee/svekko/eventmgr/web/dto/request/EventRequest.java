package ee.svekko.eventmgr.web.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventRequest {
    @NotBlank
    private String title;

    @Min(1)
    private Integer maxEnrolments;

    @NotNull
    private OffsetDateTime eventDatetime;
}
