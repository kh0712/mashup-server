package kr.mashup.branding.domain.member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MemberStatus {
    ACTIVE("활동 중"),
    INACTIVE("비활성화"),
    RUN("중도 하차");

    private final String description;
}
