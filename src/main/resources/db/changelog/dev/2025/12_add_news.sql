CREATE TABLE news
(
    id        UUID PRIMARY KEY,
    title     VARCHAR(255),
    content   TEXT,
    isVisible BOOLEAN,
    created_at TIMESTAMPTZ,
    updated_at TIMESTAMPTZ,
    city_id   UUID REFERENCES cities (id) ON DELETE CASCADE
);