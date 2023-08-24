package kr.mashup.branding.ui.member.response;

import kr.mashup.branding.domain.member.MemberProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class MemberProfileResponse {

    private Long memberId;

    private LocalDate birthDate;            // 생년월일

    private String job;                     // 직군

    private String company;                 // 회사

    private String introduction;            // 자기소개

    private String residence;               // 거주기

    private String socialNetworkServiceLink; // 인스타그램 링크

    private String githubLink;              // 깃헙 링크

    private String portfolioLink;           // 비핸스 링크

    private String blogLink;                // 티스토리 링크

    private String linkedInLink;            // 링크드인 링크

    public static MemberProfileResponse from(MemberProfile memberProfile) {
        return new MemberProfileResponse(
                memberProfile.getMemberId(),
                memberProfile.getBirthDate(),
                memberProfile.getJob(),
                memberProfile.getCompany(),
                memberProfile.getIntroduction(),
                memberProfile.getResidence(),
                memberProfile.getSocialNetworkServiceLink(),
                memberProfile.getGithubLink(),
                memberProfile.getPortfolioLink(),
                memberProfile.getBlogLink(),
                memberProfile.getLinkedInLink()
        );
    }
}
