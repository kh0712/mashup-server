package kr.mashup.branding.domain.schedule;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;

import kr.mashup.branding.domain.member.Platform;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.util.Assert;

import kr.mashup.branding.domain.BaseEntity;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.schedule.exception.ScheduleAlreadyPublishedException;
import kr.mashup.branding.util.DateRange;
import kr.mashup.branding.util.DateUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static kr.mashup.branding.domain.schedule.ScheduleType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseEntity {

    @NotNull
    private String name;

    @NotNull
    private LocalDateTime startedAt;

    @NotNull
    private LocalDateTime endedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "generation_id")
    private Generation generation;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("startedAt")
    private List<Event> eventList = new ArrayList<>();

    private LocalDateTime publishedAt;

    /*
    ScoreHistory 배치가 수행된 스케줄인지의 여부를 판단하기 위한 컬럼
     */
    private Boolean isCounted;

    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;

    @Embedded
    private Location location;

    @Enumerated(EnumType.STRING)
    private ScheduleType scheduleType;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;

    public static Schedule of(Generation generation, String name, DateRange dateRange, Location location, ScheduleType scheduleType) {
        return new Schedule(generation, name, dateRange, location, scheduleType);
    }

    public Schedule(Generation generation, String name, DateRange dateRange, Location location, ScheduleType scheduleType) {
        checkStartBeforeOrEqualEnd(dateRange.getStart(), dateRange.getEnd());

        this.generation = generation;
        this.name = name;
        this.startedAt = dateRange.getStart();
        this.endedAt = dateRange.getEnd();
        this.status = ScheduleStatus.ADMIN_ONLY;
        this.isCounted = false; // 기본값은 false 로 설정(배치가 수행되지 않음)
        this.location = location;
        this.scheduleType = scheduleType;
    }

    public void publishSchedule(){
        if(status == ScheduleStatus.PUBLIC){
            throw new ScheduleAlreadyPublishedException();
        }
        this.status = ScheduleStatus.PUBLIC;
        this.publishedAt = LocalDateTime.now();
    }

    public void changeGeneration(Generation generation){
        this.generation = generation;
    }

    public void hide(){
        // TODO: 채워넣기
        if(status != ScheduleStatus.PUBLIC){

        }
        if(startedAt.isBefore(LocalDateTime.now())){

        }
        this.status = ScheduleStatus.ADMIN_ONLY;
        this.publishedAt = null;
    }

    public void addEvent(Event event){
        this.eventList.add(event);
    }

    public void clearEvent() {
        this.eventList.clear();
    }

    public void changeName(String newName) {
        Assert.hasText(newName, "이름이 비어있을 수 없습니다.");
        this.name = newName;
    }

    public void changeDate(LocalDateTime startDate, LocalDateTime endDate) {
        checkStartBeforeOrEqualEnd(startDate, endDate);
        this.startedAt = startDate;
        this.endedAt = endDate;
    }

    public void changeLocation(Location location) {
        this.location = location;
    }

    public void changeStartDate(LocalDateTime newStartDate) {
        checkStartBeforeOrEqualEnd(newStartDate, endedAt);
        this.startedAt = newStartDate;
    }

    public void changeEndDate(LocalDateTime newEndDate) {
        checkStartBeforeOrEqualEnd(startedAt, newEndDate);
        this.endedAt = newEndDate;
    }

    public void changeScheduleType(ScheduleType scheduleType) {
        this.scheduleType = scheduleType;
    }

    private void checkStartBeforeOrEqualEnd(LocalDateTime startedAt, LocalDateTime endedAt) {
        if (!DateUtil.isStartBeforeOrEqualEnd(startedAt, endedAt)) {
            throw new IllegalArgumentException("유효하지 않은 시작시간과 끝나는 시간입니다.");
        }
    }

    public void changeIsCounted(Boolean isCounted) {
        this.isCounted = isCounted;
    }

    public Boolean isShowable() { return this.status == ScheduleStatus.PUBLIC; }

    public Boolean isOnline() {
        return this.location == null || this.location.getLatitude() == null || this.location.getLongitude() == null;
    }

    public Boolean checkAvailabilityByPlatform(Platform platform) {
        if (scheduleType == ALL) return true;
        if (scheduleType == SPRING && platform == Platform.SPRING) return true;
        if (scheduleType == IOS && platform == Platform.IOS) return true;
        if (scheduleType == DESIGN && platform == Platform.DESIGN) return true;
        if (scheduleType == WEB && platform == Platform.WEB) return true;
        if (scheduleType == NODE && platform == Platform.NODE) return true;
        return false;
    }
}
