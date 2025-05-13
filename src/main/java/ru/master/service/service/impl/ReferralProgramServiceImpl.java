package ru.master.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.master.service.constant.CertificateStatus;
import ru.master.service.constant.ClientPointType;
import ru.master.service.constant.ErrorMessage;
import ru.master.service.exception.AppException;
import ru.master.service.model.Certificate;
import ru.master.service.model.ClientPoint;
import ru.master.service.model.ClientProfile;
import ru.master.service.model.Referral;
import ru.master.service.model.dto.response.ClientReferralInfoResDto;
import ru.master.service.repository.CertificateRepo;
import ru.master.service.repository.ClientPointRepo;
import ru.master.service.repository.ClientProfileRepo;
import ru.master.service.repository.ReferralRepo;
import ru.master.service.service.ReferralProgramService;
import ru.master.service.util.AuthUtil;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@Transactional
@RequiredArgsConstructor
public class ReferralProgramServiceImpl implements ReferralProgramService {

    private final ClientPointRepo clientPointRepo;
    private final CertificateRepo certificateRepo;
    private final ReferralRepo referralRepo;

    private static final int REGISTRATION_POINTS = 300;
    private static final int FIRST_ORDER_POINTS = 200;
    private static final int CERTIFICATE_THRESHOLD = 500;
    private static final int CERTIFICATE_VALID_DAYS = 30;
    private final ClientProfileRepo clientProfileRepo;
    private final AuthUtil authUtil;

    @Override
    public ClientReferralInfoResDto getReferralInfo() {

        var user = authUtil.getAuthenticatedUser();

        ClientProfile profile = clientProfileRepo.findByUserId(user.getId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.CLIENT_PROFILE_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        return new ClientReferralInfoResDto(
                profile.getTotalEarnedPoints(),
                profile.getReferralCode()
        );
    }

    @Override
    public void addReferralRegistrationPoints(ClientProfile referrer, ClientProfile referred) {
        referralRepo.save(
                Referral.builder()
                        .referrer(referrer)
                        .referred(referred)
                        .registeredAt(Instant.now())
                        .firstOrderCompleted(false)
                        .build()
        );

        addPointsAndMaybeCertificate(referrer, REGISTRATION_POINTS, ClientPointType.REFERRAL_REGISTRATION);
    }

    @Override
    public void addReferralFirstOrderPoints(ClientProfile referrer) {
        addPointsAndMaybeCertificate(
                referrer,
                FIRST_ORDER_POINTS,
                ClientPointType.REFERRAL_FIRST_ORDER
        );
    }

    private void addPointsAndMaybeCertificate(ClientProfile referrer, int points, ClientPointType type) {
        referrer.setTotalEarnedPoints(referrer.getTotalEarnedPoints() + points);

        clientPointRepo.save(ClientPoint.builder()
                .clientProfile(referrer)
                .points(points)
                .type(type)
                .build()
        );

        if (referrer.getTotalEarnedPoints() >= CERTIFICATE_THRESHOLD) {
            certificateRepo.save(Certificate.builder()
                    .client(referrer)
                    .status(CertificateStatus.ACTIVE)
                    .issuedAt(Instant.now())
                    .expiresAt(Instant.now().plus(CERTIFICATE_VALID_DAYS, ChronoUnit.DAYS))
                    .build()
            );

            referrer.setTotalEarnedPoints(
                    Math.max(0, referrer.getTotalEarnedPoints() - CERTIFICATE_THRESHOLD)
            );
        }
    }
}
