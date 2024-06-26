package kr.mashup.branding.repository.schedule;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.schedule.Schedule;
import kr.mashup.branding.domain.schedule.ScheduleStatus;
import kr.mashup.branding.domain.schedule.ScheduleType;
import kr.mashup.branding.util.QueryUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static kr.mashup.branding.domain.generation.QGeneration.generation;
import static kr.mashup.branding.domain.schedule.QSchedule.schedule;

@RequiredArgsConstructor
public class ScheduleRepositoryCustomImpl implements ScheduleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Schedule> retrieveByGenerationAndScheduleType(Generation _generation, String searchWord, ScheduleType scheduleType, ScheduleStatus status, Pageable pageable) {
        final Sort sort = pageable.getSortOr(Sort.by(Sort.Direction.DESC, "startedAt"));

        final QueryResults<Schedule> queryResults = queryFactory
                .selectFrom(schedule)
                .join(schedule.generation, generation).fetchJoin()
                .where(generation.eq(_generation)
                        .and(schedule.scheduleType.eq(scheduleType))
                        .and(eqStatus(status))
                        .and(isContainSearchWord(searchWord)))
                .orderBy(getOrderSpecifier(sort))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return QueryUtils.toPage(queryResults, pageable);
    }

    private OrderSpecifier[] getOrderSpecifier(Sort sort) {

        final List<OrderSpecifier> orderSpecifiers = new ArrayList<>();

        for (Sort.Order order : sort) {
            Sort.Direction direction = order.getDirection();
            String field = order.getProperty();
            Order qOrder = direction.isAscending() ? Order.ASC : Order.DESC;

            final OrderSpecifier orderSpecifier
                    = new OrderSpecifier(qOrder, Expressions.path(Object.class, schedule, field));

            orderSpecifiers.add(orderSpecifier);
        }
        return orderSpecifiers.toArray(new OrderSpecifier[0]);
    }

    private BooleanExpression eqStatus(ScheduleStatus status) {
        if (status == null) {
            return null;
        }
        return schedule.status.eq(status);
    }

    public Optional<Schedule> retrieveByStartDateAndScheduleType(LocalDate startDate, ScheduleType scheduleType) {
        return Optional.ofNullable(queryFactory
                .selectFrom(schedule)
                .where(
                    schedule.startedAt.between(
                        startDate.atStartOfDay(),
                        LocalDateTime.of(startDate, LocalTime.MAX).withNano(0))
                        .and(schedule.scheduleType.eq(scheduleType))
                )
                .fetchOne());

    }

    private BooleanExpression isContainSearchWord(String searchWord) {
        if (searchWord == null) return null;

        return schedule.name.contains(searchWord);
    }
}
