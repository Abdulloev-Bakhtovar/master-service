package ru.master.service.constants;

public class ErrorMessage {
    // User related
    public static final String USER_ALREADY_EXISTS = "User with phone number already exists.";
    public static final String USER_NOT_FOUND = "User not found.";
    public static final String USER_NOT_AUTHENTICATED = "User is not authenticated";
    public static final String USER_NOT_VERIFIED = "User phone number is not verified";
    public static final String USER_ALREADY_VERIFIED = "User is already verified.";

    // Role related
    public static final String INVALID_ROLE = "Invalid role provided.";
    public static final String ASSIGN_ADMIN_ROLE_FORBIDDEN = "Only administrators can assign the ADMIN role";

    // Verification related
    public static final String INVALID_CODE_LENGTH = "Code length must be a positive number.";
    public static final String CODE_EXPIRED_OR_NOT_FOUND = "Verification code expired or not found.";
    public static final String INVALID_VERIFICATION_CODE = "Invalid verification code.";

    // JWT related
    public static final String REFRESH_TOKEN_NOT_FOUND = "Refresh token not found in cookies";
    public static final String TOKEN_BLACKLISTED = "Token blacklisted";
    public static final String TOKEN_REVOKED = "Token has been revoked";
    public static final String INVALID_TOKEN_SIGNATURE = "Invalid token signature";
    public static final String INVALID_TOKEN_TYPE = "Invalid token type";
    public static final String ONLY_ACCESS_TOKENS_ALLOWED = "Only access tokens are allowed";
    public static final String TOKEN_EXPIRED = "Token expired";

    // Client profile
    public static final String CLIENT_PROFILE_EXISTS = "Client profile already exists for this user";
    public static final String USER_AGREEMENT_ALREADY_EXISTS = "User agreement already exists for this user";
    public static final String CITY_ALREADY_EXISTS = "City with name already exists.";;
    public static final String CITY_NOT_FOUND = "City not found with specified ID";
}