package kr.mashup.branding.facade.event;

import kr.mashup.branding.domain.event.Event;
import kr.mashup.branding.domain.schedule.Schedule;
import kr.mashup.branding.service.event.EventService;
import kr.mashup.branding.service.schedule.ScheduleService;
import kr.mashup.branding.ui.event.request.EventCreateRequest;
import kr.mashup.branding.ui.event.response.EventResponse;
import kr.mashup.branding.util.DateRange;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventFacadeService {

    private final EventService eventService;
    private final ScheduleService scheduleService;

    public EventResponse create(EventCreateRequest req) {
        Schedule schedule = scheduleService.getByIdOrThrow(req.getScheduleId());
        DateRange dateRange = DateRange.of(
            req.getStartedAt(),
            req.getEndedAt()
        );

        Event event = eventService.save(
            Event.of(schedule, dateRange)
        );

        return EventResponse.from(event);
    }

}
