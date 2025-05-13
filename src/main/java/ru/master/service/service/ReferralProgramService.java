package ru.master.service.service;

import ru.master.service.model.ClientProfile;
import ru.master.service.model.dto.response.ClientReferralInfoResDto;

public interface ReferralProgramService {

    ClientReferralInfoResDto getReferralInfo();

    void addReferralRegistrationPoints(ClientProfile referrer, ClientProfile referred);

    void addReferralFirstOrderPoints(ClientProfile referrer);
}
