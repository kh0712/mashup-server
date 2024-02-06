package kr.mashup.branding.ui.schedule.request;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
public class ScheduleUpdateRequest {


    @NotNull
    private Integer generationNumber;

    @NotNull
    private String name;

    @NotNull
    private LocalDateTime startedAt;

    @NotNull
    private LocalDateTime endedAt;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    @NotNull
    private String address;

    @NotNull
    private String placeName;

    @NotEmpty
    private List<EventCreateRequest> eventsCreateRequests;
}
