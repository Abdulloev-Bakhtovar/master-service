package ru.master.service.auth.model;

import ru.master.service.constant.Role;

import java.util.HashMap;
import java.util.Map;

public record TokenClaim(String phoneNumber, Role role, String tokenType, String email) {

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();

        if (phoneNumber != null) {
            map.put("phoneNumber", phoneNumber);
        }
        if (email != null) {
            map.put("email", email);
        }
        if (role != null) {
            map.put("role", role);
        }
        map.put("tokenType", tokenType);
        return map;
    }
}
