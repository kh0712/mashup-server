package kr.mashup.branding.ui.schedule.request;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import kr.mashup.branding.domain.schedule.ScheduleType;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleCreateRequest {

    @NotNull
    private String name;

    @NotNull
    private LocalDateTime startedAt;

    @NotNull
    private LocalDateTime endedAt;

    private Double latitude;

    private Double longitude;

    private String roadAddress;

    private String detailAddress;

    private ScheduleType scheduleType = ScheduleType.ALL;

    @NotEmpty
    private List<EventCreateRequest> eventsCreateRequests;

    private ScheduleCreateRequest(String name, LocalDateTime startedAt, LocalDateTime endedAt, List<EventCreateRequest> eventsCreateRequests) {
        this.name = name;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.eventsCreateRequests = eventsCreateRequests;
    }


    public static ScheduleCreateRequest from(ScheduleUpdateRequest request){
        return new ScheduleCreateRequest(request.getName(), request.getStartedAt(), request.getEndedAt(), request.getEventsCreateRequests());
    }

}
