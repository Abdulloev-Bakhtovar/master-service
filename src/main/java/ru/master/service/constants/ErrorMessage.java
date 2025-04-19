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
    public static final String MALFORMED_JWT_TOKEN = "JWT token is malformed";
    public static final String MISSING_JWT_ROLES = "JWT token missing required roles";

    // JWT Key related
    public static final String PRIVATE_KEY_FILE_NOT_FOUND = "Private key file not found. Please check the file path.";
    public static final String PUBLIC_KEY_FILE_NOT_FOUND = "Public key file not found. Please check the file path.";
    public static final String JWT_SIGNING_ERROR = "Error while signing JWT";
    public static final String JWT_PARSING_ERROR = "Error while parsing JWT";

    // Client profile
    public static final String CLIENT_PROFILE_EXISTS = "Client profile already exists for this user";
    public static final String USER_AGREEMENT_ALREADY_EXISTS = "User agreement already exists for this user";

    // City related
    public static final String CITY_ALREADY_EXISTS = "City with name already exists.";
    public static final String CITY_NOT_FOUND = "City not found with specified ID";

    // Master profile
    public static final String MASTER_PROFILE_EXISTS = "Master profile already exists for this user";

    // Document photo
    public static final String UNSUPPORTED_FILE_TYPE = "Unsupported file type.";
    public static final String INVALID_FILE_NAME = "Invalid file name. File must have an extension.";

    public static final String ENTITY_ALREADY_EXISTS = "%s already exists.";
    public static final String ENTITY_NOT_FOUND = "%s not found.";
    public static final String INVALID_ENTITY_STATE = "Invalid state for %s.";
}