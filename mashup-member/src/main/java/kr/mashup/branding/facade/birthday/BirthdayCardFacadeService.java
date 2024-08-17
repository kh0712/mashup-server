package kr.mashup.branding.facade.birthday;

import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.member.MemberGeneration;
import kr.mashup.branding.domain.randommessage.RandomMessage;
import kr.mashup.branding.service.birthday.BirthdayCardService;
import kr.mashup.branding.service.member.MemberService;
import kr.mashup.branding.ui.birthday.request.BirthdayCardRequest;
import kr.mashup.branding.ui.birthday.response.BirthdayCardDefaultImageResponse;
import kr.mashup.branding.ui.birthday.response.BirthdayCardDefaultImagesResponse;
import kr.mashup.branding.ui.birthday.response.BirthdayCardResponse;
import kr.mashup.branding.ui.birthday.response.BirthdayCardsResponse;
import kr.mashup.branding.ui.danggn.response.DanggnRandomMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BirthdayCardFacadeService {

    private final BirthdayCardService birthdayCardService;
    private final MemberService memberService;

    @Transactional(readOnly = true)
    public BirthdayCardDefaultImagesResponse getDefault() {
        List<BirthdayCardDefaultImageResponse> cardImages = birthdayCardService.getDefault()
            .stream()
            .map(cardImage -> BirthdayCardDefaultImageResponse.of(cardImage.getImageUrl()))
            .collect(Collectors.toList());

        return BirthdayCardDefaultImagesResponse.of(cardImages);
    }

    @Transactional
    public void send(Long memberId, BirthdayCardRequest request) {
        Member senderMember = memberService.findMemberById(memberId);
        MemberGeneration latestMemberGeneration = memberService.getCurrentMemberGeneration(senderMember);

        birthdayCardService.checkAlreadySent(request.getRecipientMemberId(), senderMember, latestMemberGeneration);
        birthdayCardService.send(request.getRecipientMemberId(), senderMember, latestMemberGeneration, request.getMessage(), request.getImageUrl());
    }

    @Transactional(readOnly = true)
    public BirthdayCardsResponse getMy(Long memberId) {
        Member member = memberService.findMemberById(memberId);
        MemberGeneration generation = memberService.getCurrentMemberGeneration(member);

        List<BirthdayCardResponse> birthdayCards = birthdayCardService.getMy(member, generation)
            .stream()
            .map(birthdayCard -> BirthdayCardResponse.of(
                birthdayCard.getId(),
                birthdayCard.getSenderMemberName(),
                birthdayCard.getSenderMemberPlatform().getName(),
                birthdayCard.getMessage(),
                birthdayCard.getImageUrl()
            ))
            .collect(Collectors.toList());

        return BirthdayCardsResponse.of(birthdayCards);
    }

    @Transactional(readOnly = true)
    public DanggnRandomMessageResponse getRandomMessage() {
        List<RandomMessage> randomMessageList = birthdayCardService.findAll();
        Collections.shuffle(randomMessageList);
        return DanggnRandomMessageResponse.from(randomMessageList.get(0));
    }
}
