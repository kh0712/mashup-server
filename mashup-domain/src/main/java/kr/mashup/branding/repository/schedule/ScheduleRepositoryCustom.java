package kr.mashup.branding.repository.schedule;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.schedule.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface ScheduleRepositoryCustom {

    Page<Schedule> findByGeneration(Generation generation, Pageable pageable);

    Schedule retrieveByStartedAt(LocalDate startDate);
}
