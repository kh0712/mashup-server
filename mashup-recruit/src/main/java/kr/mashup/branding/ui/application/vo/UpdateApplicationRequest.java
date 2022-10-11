package kr.mashup.branding.ui.application.vo;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import kr.mashup.branding.domain.application.UpdateApplicationVo;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UpdateApplicationRequest {
    /**
     * 지원자 이름
     */
    private String applicantName;
    /**
     * 지원자 연락처
     */
    private String phoneNumber;
    /**
     * 지원자 생년월일
     */
    private LocalDate birthdate;
    /**
     * 지원자 소속
     */
    private String department;
    /**
     * 지원자 거주지
     */
    private String residence;
    /**
     * 각 질문에 대한 답변
     */
    private List<AnswerRequest> answers;
    /**
     * 개인정보처리방침 동의여부
     */
    private Boolean privacyPolicyAgreed;

    public UpdateApplicationVo toVo(){
        return UpdateApplicationVo.of(
            applicantName,
            phoneNumber,
            birthdate,
            department,
            residence,
            answers.stream()
                .map(AnswerRequest::toVo)
                .collect(Collectors.toList()),
            privacyPolicyAgreed);
    }
}
