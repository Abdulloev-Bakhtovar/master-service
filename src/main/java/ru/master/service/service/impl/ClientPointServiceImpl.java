package ru.master.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.master.service.constant.CertificateStatus;
import ru.master.service.constant.ClientPointType;
import ru.master.service.model.Certificate;
import ru.master.service.model.ClientPoint;
import ru.master.service.model.ClientProfile;
import ru.master.service.repository.CertificateRepo;
import ru.master.service.repository.ClientPointRepo;
import ru.master.service.service.ClientPointService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientPointServiceImpl implements ClientPointService {

    private final ClientPointRepo clientPointRepo;

    private final int referralRegistrationPoint = 300;
    private final int pointForGetCertificate = 500;
    private final int certificateExpiresAt = 30; // 30 дней
    private final CertificateRepo certificateRepo;

    @Override
    public void addReferralRegistrationPoints(ClientProfile referrer) {
        ClientPoint point = ClientPoint.builder()
                .clientProfile(referrer)
                .points(referralRegistrationPoint)
                .type(ClientPointType.REFERRAL_REGISTRATION)
                .build();

        referrer.setTotalEarnedPoints(referrer.getTotalEarnedPoints() + point.getPoints());

        if (referrer.getTotalEarnedPoints() >= pointForGetCertificate) {
            Certificate certificate = Certificate.builder()
                    .client(referrer)
                    .status(CertificateStatus.ACTIVE)
                    .issuedAt(Instant.now())
                    .expiresAt(Instant.now().plus(certificateExpiresAt, ChronoUnit.DAYS))
                    .build();

            certificateRepo.save(certificate);

            referrer.setTotalEarnedPoints(referrer.getTotalEarnedPoints() - pointForGetCertificate);
            if (referrer.getTotalEarnedPoints() < 0) {
                referrer.setTotalEarnedPoints(0);
            }
        }
        clientPointRepo.save(point);
    }

}
