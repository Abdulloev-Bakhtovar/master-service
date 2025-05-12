CREATE TABLE admin_profiles
(
    id         UUID PRIMARY KEY,
    name       VARCHAR(100)        NOT NULL,
    password   VARCHAR(500)        NOT NULL,
    email      VARCHAR(255) UNIQUE NOT NULL,
    role       VARCHAR(50)         NOT NULL,
    created_at TIMESTAMPTZ,
    updated_at TIMESTAMPTZ
);

CREATE INDEX idx_admin_profiles_email ON admin_profiles(email);