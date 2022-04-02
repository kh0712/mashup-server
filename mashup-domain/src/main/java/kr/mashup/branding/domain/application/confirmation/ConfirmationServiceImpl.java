package kr.mashup.branding.domain.application.confirmation;

import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.application.ApplicationService;
import kr.mashup.branding.domain.application.ApplicationStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ConfirmationServiceImpl implements ConfirmationService {
    private static final String RECRUITMENT_ENDED = "RECRUITMENT_ENDED";

    private final ApplicationService applicationService;

    @Override
    @Transactional
    public void updateToBeDeterminedToNotApplicable() {
        List<Application> applications = applicationService.getApplicationsByApplicationStatusAndEventName(
            ApplicationStatus.WRITING, RECRUITMENT_ENDED
        );
        applications.stream()
            .filter(application ->
                application.getConfirmation().getStatus().equals(ApplicantConfirmationStatus.TO_BE_DETERMINED)
            )
            .forEach(application -> {
                    application.updateConfirmationStatus(ApplicantConfirmationStatus.NOT_APPLICABLE);
                    log.info("[updateToBeDeterminedToNotApplicable] applicationId: " + application.getApplicationId());
                }
            );
    }
}
