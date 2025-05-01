package ru.master.service.auth.model;

import ru.master.service.constant.Role;

import java.util.HashMap;
import java.util.Map;

public record TokenClaim(String phoneNumber, Role role, String tokenType) {

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("phoneNumber", phoneNumber);
        if (role != null) {
            map.put("role", role);
        }
        map.put("tokenType", tokenType);
        return map;
    }
}
