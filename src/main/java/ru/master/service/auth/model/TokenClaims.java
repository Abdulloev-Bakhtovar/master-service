package ru.master.service.auth.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record TokenClaims(String phoneNumber, List<String> roles, String tokenType) {
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("phoneNumber", phoneNumber);
        if (roles != null) {
            map.put("roles", roles);
        }
        map.put("tokenType", tokenType);
        return map;
    }
}
