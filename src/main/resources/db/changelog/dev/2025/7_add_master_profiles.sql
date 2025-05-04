CREATE TABLE master_profiles
(
    id              UUID PRIMARY KEY,
    first_name      VARCHAR(100)        NOT NULL,
    last_name       VARCHAR(100)        NOT NULL,
    city_id         UUID REFERENCES cities (id),
    user_id         UUID REFERENCES users (id) UNIQUE,
    email           VARCHAR(255) UNIQUE NOT NULL,
    marital_status  VARCHAR(50),
    education       VARCHAR(100),
    master_status   VARCHAR(50),
    work_experience TEXT,
    has_conviction  BOOLEAN,
    average_rating  NUMERIC(2, 1),
    rating_count    BIGINT,
    created_at      TIMESTAMPTZ,
    updated_at      TIMESTAMPTZ
);

CREATE INDEX idx_master_profiles_city ON master_profiles(city_id);
CREATE INDEX idx_master_profiles_email ON master_profiles(email);