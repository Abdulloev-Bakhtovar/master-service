package ru.master.service.mapper;

import ru.master.service.auth.model.User;
import ru.master.service.constant.VerificationStatus;
import ru.master.service.model.MasterRequest;

public interface MasterRequestMapper {

    MasterRequest toMasterRequestEntity(User user, VerificationStatus verificationStatus);
}
