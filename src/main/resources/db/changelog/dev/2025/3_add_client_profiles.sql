CREATE TABLE IF NOT EXISTS client_profiles
(
    id         UUID PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    address    VARCHAR(255) NOT NULL,
    city_id    UUID         REFERENCES cities (id) ON DELETE SET NULL,
    user_id    UUID REFERENCES users (id) ON DELETE CASCADE UNIQUE,
    created_at TIMESTAMPTZ,
    updated_at TIMESTAMPTZ
);

CREATE INDEX IF NOT EXISTS idx_client_profiles_city ON client_profiles(city_id);
CREATE INDEX IF NOT EXISTS idx_client_profiles_user ON client_profiles(user_id);