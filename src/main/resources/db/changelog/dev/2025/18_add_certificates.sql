CREATE TABLE certificates
(
    id                UUID PRIMARY KEY,
    client_profile_id UUID               NOT NULL,
    status            VARCHAR(50)        NOT NULL,
    issued_at         TIMESTAMPTZ        NOT NULL,
    expires_at        TIMESTAMPTZ        NOT NULL,

    FOREIGN KEY (client_profile_id) REFERENCES client_profiles (id)
);