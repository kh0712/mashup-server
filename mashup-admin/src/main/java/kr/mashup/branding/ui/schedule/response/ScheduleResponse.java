package kr.mashup.branding.ui.schedule.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import kr.mashup.branding.domain.schedule.Schedule;
import kr.mashup.branding.domain.schedule.ScheduleStatus;
import lombok.Getter;
import lombok.Value;

@Getter
@Value(staticConstructor = "of")
public class ScheduleResponse {

    Long scheduleId;

    String name;

    LocalDateTime startedAt;

    LocalDateTime endedAt;

    Integer generationNumber;

    Double latitude;

    Double longitude;

    List<EventResponse> eventList;

    ScheduleStatus status;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    LocalDateTime publishedAt;

    public static ScheduleResponse from(Schedule schedule) {
        return ScheduleResponse.of(
            schedule.getId(),
            schedule.getName(),
            schedule.getStartedAt(),
            schedule.getEndedAt(),
            schedule.getGeneration().getNumber(),
            schedule.getLocation() == null ? null : schedule.getLocation().getLatitude(),
            schedule.getLocation() == null ? null : schedule.getLocation().getLongitude(),
            schedule.getEventList()
                .stream()
                .map(EventResponse::from)
                .collect(Collectors.toList()),
            schedule.getStatus(),
            schedule.getCreatedAt(),
            schedule.getUpdatedAt(),
            schedule.getPublishedAt()
        );
    }
}
