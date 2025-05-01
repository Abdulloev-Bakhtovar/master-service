CREATE TABLE IF NOT EXISTS cities
(
    id         UUID PRIMARY KEY,
    name       VARCHAR(255) NOT NULL UNIQUE,
    is_visible BOOLEAN NOT NULL,
    created_at TIMESTAMPTZ,
    updated_at TIMESTAMPTZ
);