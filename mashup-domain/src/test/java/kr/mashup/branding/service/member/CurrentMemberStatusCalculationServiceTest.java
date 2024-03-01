package kr.mashup.branding.service.member;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.CurrentMemberStatus;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.member.MemberGeneration;
import kr.mashup.branding.domain.member.MemberGenerationStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import javax.swing.*;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CurrentMemberStatusCalculationServiceTest {

    private CurrentMemberStatusCalculationService sut;

    @BeforeEach
    void setUp() {
        sut = new CurrentMemberStatusCalculationService();
    }

    @Test
    @DisplayName("getCurrentStatus_13 기수 활동 중 중도 하차_13기로 조회 시 DROP_OUT")
    public void getCurrentStatus_test(){
        // given
        Generation generation = mock(Generation.class);
        when(generation.getNumber()).thenReturn(13);

        MemberGeneration mg = mock(MemberGeneration.class);
        when(mg.getGeneration()).thenReturn(generation);
        when(mg.getStatus()).thenReturn(MemberGenerationStatus.DROP_OUT);

        Member member = mock(Member.class);
        when(member.getMemberGenerations()).thenReturn(List.of(mg));
        when(member.getId()).thenReturn(1L);

        // when
        Map<Long, CurrentMemberStatus> currentStatus = sut.getCurrentStatus(13, List.of(member));

        // then
        Assertions.assertThat(currentStatus.get(1L)).isEqualTo(CurrentMemberStatus.DROP_OUT);
    }

    @Test
    @DisplayName("getCurrentStatus_13 기수 활동 중 중도 하차_14기로 조회 시_데이터 없음")
    public void getCurrentStatus_test2(){
        // given
        Generation generation = mock(Generation.class);
        when(generation.getNumber()).thenReturn(13);

        MemberGeneration mg = mock(MemberGeneration.class);
        when(mg.getGeneration()).thenReturn(generation);
        when(mg.getStatus()).thenReturn(MemberGenerationStatus.DROP_OUT);

        Member member = mock(Member.class);
        when(member.getMemberGenerations()).thenReturn(List.of(mg));
        when(member.getId()).thenReturn(1L);

        // when
        Map<Long, CurrentMemberStatus> currentStatus = sut.getCurrentStatus(14, List.of(member));

        // then
        Assertions.assertThat(currentStatus.get(1L)).isNull();
    }

    @Test
    @DisplayName("getCurrentStatus 13기 활동 정상 종료. 14기 안함. 13기 종료 후 13기로 조회 시 활동 종료")
    public void getCurrentStatus_test3(){
        // given
        Generation generation = mock(Generation.class);
        when(generation.getNumber()).thenReturn(13);
        when(generation.isInProgress(any())).thenReturn(false);

        MemberGeneration mg = mock(MemberGeneration.class);
        when(mg.getGeneration()).thenReturn(generation);
        when(mg.getStatus()).thenReturn(MemberGenerationStatus.ACTIVE);

        Member member = mock(Member.class);
        when(member.getMemberGenerations()).thenReturn(List.of(mg));
        when(member.getId()).thenReturn(1L);

        // when
        Map<Long, CurrentMemberStatus> currentStatus = sut.getCurrentStatus(13, List.of(member));

        // then
        Assertions.assertThat(currentStatus.get(1L)).isEqualTo(CurrentMemberStatus.END);
    }

    @Test
    @DisplayName("getCurrentStatus 13기 활동 정상 종료. 14기 안함. 13기 종료 후 14기로 조회 시 데이터 없음")
    public void getCurrentStatus_test4(){
        // given
        Generation generation = mock(Generation.class);
        when(generation.getNumber()).thenReturn(13);
        when(generation.isInProgress(any())).thenReturn(false);

        MemberGeneration mg = mock(MemberGeneration.class);
        when(mg.getGeneration()).thenReturn(generation);
        when(mg.getStatus()).thenReturn(MemberGenerationStatus.ACTIVE);

        Member member = mock(Member.class);
        when(member.getMemberGenerations()).thenReturn(List.of(mg));
        when(member.getId()).thenReturn(1L);

        // when
        Map<Long, CurrentMemberStatus> currentStatus = sut.getCurrentStatus(14, List.of(member));

        // then
        Assertions.assertThat(currentStatus.get(1L)).isNull();
    }
}