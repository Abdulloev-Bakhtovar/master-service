package ru.master.service.auth.service;

import java.util.UUID;

public interface UserService {

    String getVerificationStatusByUserId(UUID id);
}
