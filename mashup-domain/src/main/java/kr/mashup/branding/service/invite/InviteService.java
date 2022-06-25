package kr.mashup.branding.service.invite;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.invite.Invite;
import kr.mashup.branding.domain.member.Platform;
import kr.mashup.branding.repository.invite.InviteRepository;
import kr.mashup.branding.util.DateRange;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InviteService {
	private final InviteRepository inviteRepository;

	@Transactional
	public Invite create(Platform platform, Generation generation, DateRange dateRange) {
		Invite invite = Invite.of(platform, generation, dateRange);

		return inviteRepository.save(invite);
	}
}
