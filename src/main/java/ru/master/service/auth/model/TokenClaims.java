package ru.master.service.auth.model;

import ru.master.service.constants.Role;
import ru.master.service.constants.VerificationStatus;

import java.util.HashMap;
import java.util.Map;

public record TokenClaims(String phoneNumber, Role role, String tokenType, VerificationStatus verificationStatus) {
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("phoneNumber", phoneNumber);
        map.put("verificationStatus", verificationStatus);
        if (role != null) {
            map.put("role", role);
        }
        map.put("tokenType", tokenType);
        return map;
    }
}
