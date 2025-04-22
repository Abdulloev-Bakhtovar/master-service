package ru.master.service.mapper;

import ru.master.service.auth.model.User;
import ru.master.service.constants.VerificationStatus;
import ru.master.service.model.MasterApplication;

public interface MasterApplicationMapper {

    MasterApplication toEntity(User user, VerificationStatus verificationStatus);

    void toEntity(User admin,
                  User user,
                  VerificationStatus verificationStatus,
                  MasterApplication existsApplication,
                  String rejectionReason);
}
