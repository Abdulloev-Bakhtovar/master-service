CREATE TABLE client_points
(
    id                UUID PRIMARY KEY,
    points            INT NOT NULL,
    type              VARCHAR(50),
    created_at        TIMESTAMPTZ,
    updated_at        TIMESTAMPTZ,
    client_profile_id UUID UNIQUE,

    FOREIGN KEY (client_profile_id) REFERENCES client_profiles (id)
);