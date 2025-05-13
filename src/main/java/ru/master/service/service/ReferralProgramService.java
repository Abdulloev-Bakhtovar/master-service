package ru.master.service.service;

import ru.master.service.model.ClientProfile;

public interface ReferralProgramService {

    void addReferralRegistrationPoints(ClientProfile referrer, ClientProfile referred);

    void addReferralFirstOrderPoints(ClientProfile referrer);
}
