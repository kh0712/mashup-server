package kr.mashup.branding.domain.applicant;

import lombok.Value;

@Value(staticConstructor = "of")
public class LoginResponseVo {
    String accessToken;
    Long applicantId;
    String email;
    String status;
}
