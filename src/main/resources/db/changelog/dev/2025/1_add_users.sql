CREATE TABLE IF NOT EXISTS users
(
    id                  UUID PRIMARY KEY,
    phone_number        VARCHAR(30) NOT NULL UNIQUE,
    created_at          TIMESTAMPTZ,
    updated_at          TIMESTAMPTZ,
    role                VARCHAR(50) NOT NULL,
    verification_status VARCHAR(50) NOT NULL
);