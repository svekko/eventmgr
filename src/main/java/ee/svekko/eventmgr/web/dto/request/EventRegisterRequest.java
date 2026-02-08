package ee.svekko.eventmgr.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventRegisterRequest {
    @NotNull
    private UUID eventId;

    @NotBlank
    private String idCode;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;
}
