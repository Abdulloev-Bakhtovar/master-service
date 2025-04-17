package ru.master.service.auth.service;

public interface TokenBlacklistService {

    void addToBlacklist(String token);

    boolean isBlacklisted(String token);
}
