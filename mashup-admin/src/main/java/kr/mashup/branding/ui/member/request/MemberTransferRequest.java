package kr.mashup.branding.ui.member.request;

import kr.mashup.branding.domain.member.Platform;
import lombok.Getter;

import java.util.List;

@Getter
public class MemberTransferRequest {
    private Integer oldGenerationNumber;
    private Integer newGenerationNumber;
    private List<Long> memberIds;
    private Platform platform;
}
