package ru.master.service.auth.service;

public interface UserService {

    String getVerificationStatusByPhoneNumber(String phoneNumber);
}
