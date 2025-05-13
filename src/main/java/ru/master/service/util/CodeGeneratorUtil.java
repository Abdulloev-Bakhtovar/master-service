package ru.master.service.util;

import org.springframework.http.HttpStatus;
import ru.master.service.constant.ErrorMessage;
import ru.master.service.exception.AppException;

import java.security.SecureRandom;

public class CodeGeneratorUtil {

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final String REFERRAL_CODE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int referralCodeLength = 10;

    /**
     * Генерирует числовой код указанной длины
     * @param length длина генерируемого кода (количество цифр)
     * @return сгенерированный числовой код
     * @throws AppException если длина меньше 1
     */
    public static String generateNumericCode(int length) throws AppException {

        if (length < 1) {
            throw new AppException(
                    ErrorMessage.INVALID_CODE_LENGTH,
                    HttpStatus.BAD_REQUEST
            );
        }

        StringBuilder code = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            code.append(secureRandom.nextInt(10));
        }

        return code.toString();
    }

    /**
     * Генерирует реферальный код, состоящий из букв и цифр
     * @return сгенерированный реферальный код
     * @throws AppException если длина меньше 1
     */
    public static String generateReferralCode() throws AppException {
        if (referralCodeLength < 1) {
            throw new AppException(
                    ErrorMessage.INVALID_CODE_LENGTH,
                    HttpStatus.BAD_REQUEST
            );
        }

        StringBuilder code = new StringBuilder(referralCodeLength);
        for (int i = 0; i < referralCodeLength; i++) {
            int index = secureRandom.nextInt(REFERRAL_CODE_CHARS.length());
            code.append(REFERRAL_CODE_CHARS.charAt(index));
        }

        return code.toString();
    }
}
