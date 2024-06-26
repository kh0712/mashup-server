package kr.mashup.branding.ui.schedule.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import kr.mashup.branding.domain.schedule.Location;
import kr.mashup.branding.domain.schedule.Schedule;
import kr.mashup.branding.domain.schedule.ScheduleType;
import lombok.Getter;
import lombok.Value;

@Getter
@Value(staticConstructor = "of")
public class ScheduleResponse {

    Long scheduleId;

    String name;

    Integer dateCount;

    LocalDateTime startedAt;

    LocalDateTime endedAt;

    Integer generationNumber;

    Location location;

    ScheduleType scheduleType;

    List<EventResponse> eventList;

    public static ScheduleResponse from(Schedule schedule, Integer dateCount) {
        return ScheduleResponse.of(
            schedule.getId(),
            schedule.getName(),
            dateCount,
            schedule.getStartedAt(),
            schedule.getEndedAt(),
            schedule.getGeneration().getNumber(),
            schedule.getLocation() == null ? null : schedule.getLocation(),
            schedule.getScheduleType(),
            schedule.getEventList()
                .stream()
                .map(EventResponse::from)
                .collect(Collectors.toList())
        );
    }
}
