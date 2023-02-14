package kr.mashup.branding.repository.attendance;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.mashup.branding.domain.attendance.Attendance;
import kr.mashup.branding.domain.attendance.QAttendance;
import kr.mashup.branding.domain.schedule.QEvent;
import kr.mashup.branding.domain.schedule.QSchedule;
import kr.mashup.branding.domain.schedule.Schedule;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CustomAttendanceRepositoryImpl implements CustomAttendanceRepository {

	private final JPAQueryFactory jpaQueryFactory;

	private final QAttendance qAttendance = QAttendance.attendance;
	private final QSchedule qSchedule = QSchedule.schedule;
	private final QEvent qEvent = QEvent.event;

	public List<Attendance> findBySchedule(Schedule schedule) {
		return jpaQueryFactory
			.select(qAttendance)
			.from(qAttendance)
			.innerJoin(qEvent)
			.on(qAttendance.event.eq(qEvent))
			.innerJoin(qSchedule)
			.on(qEvent.schedule.eq(qSchedule))
			.where(qSchedule.eq(schedule))
			.fetch();
	}
}
